package com.campuseats;

import com.campuseats.metrica.*;
import com.campuseats.repositorio.RepoAnaliticaEnMemoria;
import com.campuseats.repositorio.RepositorioAnalitica;
import com.campuseats.servicio.ServicioAnalitica;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RepositorioAnalitica repo = new RepoAnaliticaEnMemoria();

        LocalDateTime desde = LocalDateTime.of(2026, 10, 19, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 10, 30, 23, 59);

        // Strategy: cada metrica se inyecta; agregar una nueva no obliga a tocar el servicio.
        ServicioAnalitica servicio = new ServicioAnalitica(repo,
                List.of(new PlatosTop(), new PedidosPorHora(), new TicketPromedio(),
                        new TiempoPreparacion(), new PagosRechazados(repo, desde, hasta)));

        servicio.cargar();

        System.out.println("=== Estadisticas del 19 al 30 de octubre ===");
        Map<String, Object> resumen = servicio.resumen(desde, hasta);
        resumen.forEach((nombre, valor) -> System.out.println("[" + nombre + "]\n" + valor + "\n"));
    }
}