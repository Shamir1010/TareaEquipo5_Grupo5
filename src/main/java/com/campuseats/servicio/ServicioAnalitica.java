package com.campuseats.servicio;

import com.campuseats.filtro.*;
import com.campuseats.lector.FabricaFuentes;
import com.campuseats.metrica.Metrica;
import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Pago;
import com.campuseats.modelo.Plato;
import com.campuseats.repositorio.RepositorioAnalitica;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

/** Facade: unica puerta de entrada. cargar() lee, limpia y guarda; resumen() calcula las metricas. */
public class ServicioAnalitica {

    private static final Path CONFIG = Path.of("config/fuentes.json");

    private final RepositorioAnalitica repositorio;
    private final List<Metrica> metricas;
    private final FabricaFuentes fabrica;

    public ServicioAnalitica(RepositorioAnalitica repositorio, List<Metrica> metricas) {
        this.repositorio = repositorio;
        this.metricas = metricas;
        this.fabrica = new FabricaFuentes();
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        System.out.println("Iniciando carga de datos a traves de ServicioAnalitica...");

        List<LineaPedido> lineas = new ArrayList<>();
        List<Pago> pagos = new ArrayList<>();
        List<Plato> menu = new ArrayList<>();

        try {
            // 1. Factory: el tipo de cada fuente sale de config/fuentes.json
            JsonNode fuentes = new ObjectMapper().readTree(CONFIG.toFile()).get("fuentes");
            for (JsonNode f : fuentes) {
                String tipo = f.get("tipo").asText();
                List<?> datos = fabrica.crear(tipo, Path.of(f.get("ruta").asText())).leer();
                switch (tipo) {
                    case "app_json" -> lineas.addAll((List<LineaPedido>) datos);
                    case "pasarela_csv" -> pagos.addAll((List<Pago>) datos);
                    case "menu_csv" -> menu.addAll((List<Plato>) datos);
                    default -> System.err.println("Tipo sin uso en el servicio: " + tipo);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo leer " + CONFIG + ": " + e.getMessage(), e);
        }

        // 2. Pipeline: primero se limpia, despues se completa la informacion
        Pipeline pipeline = new Pipeline()
                .agregar(new FiltroDuplicados())
                .agregar(new FiltroCantidadValida())
                .agregar(new FiltroSoloPagados())
                .agregar(new FiltroPagoAprobado(pagos))
                .agregar(new FiltroNombrePlato(menu));
        List<LineaPedido> limpias = pipeline.ejecutar(lineas);

        System.out.println("Lineas leidas (sin limpiar): " + lineas.size());
        System.out.println("--- Limpieza de los datos ---");
        pipeline.descartes().forEach((regla, n) -> System.out.printf("  %-62s %d lineas descartadas%n", regla, n));
        long pedidos = limpias.stream().map(LineaPedido::numeroPedido).distinct().count();
        System.out.println("  Quedan al final: " + limpias.size() + " lineas de " + pedidos + " pedidos");

        // 3. Repository
        repositorio.guardarLineas(limpias);
        repositorio.guardarPagos(pagos);
        System.out.println("Carga finalizada: " + limpias.size() + " lineas y " + pagos.size() + " pagos guardados en repositorio.\n");
    }

    public Map<String, Object> resumen(LocalDateTime desde, LocalDateTime hasta) {
        List<LineaPedido> filtradas = repositorio.lineasEntre(desde, hasta);
        Map<String, Object> resultados = new LinkedHashMap<>();
        if (metricas != null) {
            for (Metrica m : metricas) resultados.put(m.nombre(), m.calcular(filtradas));
        }
        return resultados;
    }
}
