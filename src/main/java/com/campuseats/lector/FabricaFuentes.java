package com.campuseats.lector;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FabricaFuentes {
    private final Map<String, Function<Path, FuenteDatos<?>>> tipos = new HashMap<>();

    public FabricaFuentes() {
        tipos.put("app_json", AppPedidosJsonAdapter::new);
        tipos.put("pasarela_csv", PasarelaCsvAdapter::new);
        tipos.put("menu_csv", MenuCsvAdapter::new);
    }

    public FuenteDatos<?> crear(String tipo, Path rutaBase) {
        Function<Path, FuenteDatos<?>> constructor = tipos.get(tipo);
        if (constructor == null) {
            throw new IllegalArgumentException("Tipo de fuente desconocido o no soportado: " + tipo);
        }
        return constructor.apply(rutaBase);
    }
}