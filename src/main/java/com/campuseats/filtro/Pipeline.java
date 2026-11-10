package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Ejecuta los filtros en orden e informa cuantas lineas descarto cada uno. */
public class Pipeline {

    private final List<Filtro> filtros = new ArrayList<>();
    private final Map<String, Integer> descartes = new LinkedHashMap<>();

    public Pipeline agregar(Filtro filtro) {
        filtros.add(filtro);
        return this;
    }

    public List<LineaPedido> ejecutar(List<LineaPedido> entrada) {
        descartes.clear();
        List<LineaPedido> actual = entrada;
        for (Filtro f : filtros) {
            List<LineaPedido> salida = f.aplicar(actual);
            descartes.put(f.nombre(), actual.size() - salida.size());
            actual = salida;
        }
        return actual;
    }

    public Map<String, Integer> descartes() {
        return descartes;
    }
}
