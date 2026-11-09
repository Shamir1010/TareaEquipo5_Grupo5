package com.campuseats.servicio;

import com.campuseats.lector.FabricaFuentes;
import com.campuseats.lector.FuenteDatos;
import com.campuseats.metrica.Metrica;
import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Pago;
import com.campuseats.repositorio.RepositorioAnalitica;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServicioAnalitica {

    private final RepositorioAnalitica repositorio;
    private final List<Metrica> metricas;
    private final FabricaFuentes fabrica;

    public ServicioAnalitica(RepositorioAnalitica repositorio, List<Metrica> metricas) {
        this.repositorio = repositorio;
        this.metricas = metricas;
        this.fabrica = new FabricaFuentes();
    }

    //Lee las fuentes mediante la fábrica y adaptadores, aplica la carga inicial y guarda los datos en el repositorio.
    @SuppressWarnings("unchecked")
    public void cargar() {
        System.out.println("Iniciando carga de datos a traves de ServicioAnalitica...");

        // 1. Instanciar los lectores mediante la fabrica de fuentes
        FuenteDatos<LineaPedido> lectorPedidos = (FuenteDatos<LineaPedido>) fabrica.crear("app_json", Path.of("datos/pedidos_app.json"));
        FuenteDatos<Pago> lectorPagos = (FuenteDatos<Pago>) fabrica.crear("pasarela_csv", Path.of("datos/pagos_pasarela.csv"));

        List<LineaPedido> lineas = lectorPedidos.leer();
        List<Pago> pagos = lectorPagos.leer();

        // 2. Persistir en el repositorio
        repositorio.guardarLineas(lineas);
        repositorio.guardarPagos(pagos);

        System.out.println("Carga finalizada: " + lineas.size() + " lineas y " + pagos.size() + " pagos guardados en repositorio.\n");
    }

    //Consulta las lineas del repositorio en el rango dado y calcula las metricas configuradas.
    public Map<String, Object> resumen(LocalDateTime desde, LocalDateTime hasta) {
        List<LineaPedido> lineasFiltradas = repositorio.lineasEntre(desde, hasta);
        Map<String, Object> resultados = new LinkedHashMap<>();

        if (metricas != null) {
            for (Metrica m : metricas) {
                resultados.put(m.nombre(), m.calcular(lineasFiltradas));
            }
        }
        return resultados;
    }
}