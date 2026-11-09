package com.campuseats.lector;

import java.nio.file.Path;
import java.util.List;

public abstract class FuenteDatos<T> {

    protected Path ruta;
    public FuenteDatos(Path ruta) {
        this.ruta = ruta;
    }

    public abstract List<T> leer();
}
