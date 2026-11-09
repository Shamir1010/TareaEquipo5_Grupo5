package com.campuseats;

import com.campuseats.repositorio.RepositorioAnalitica;
import com.campuseats.repositorio.RepoAnaliticaEnMemoria;
import com.campuseats.servicio.ServicioAnalitica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciar el Repositorio en memoria
        RepositorioAnalitica repo = new RepoAnaliticaEnMemoria();

        // 2. Instanciar el Servicio Fachada
        ServicioAnalitica servicio = new ServicioAnalitica(repo, new ArrayList<>());

        servicio.cargar();

        LocalDateTime desde = LocalDateTime.of(2026, 10, 19, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2026, 10, 30, 23, 59);

        Map<String, Object> resumen = servicio.resumen(desde, hasta);
        System.out.println("Lineas almacenadas en el periodo: " + repo.lineasEntre(desde, hasta).size());
        System.out.println("Fachada y Repositorio funcionando correctamente.");
    }
}