package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;
import java.util.List;
import java.util.stream.Collectors;

/** Regla: la cantidad de cada plato debe ser un entero mayor que cero. */
public class FiltroCantidadValida implements Filtro {
    public String nombre() { return "Cantidad igual a cero (o negativa)"; }

    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream().filter(l -> l.cantidad() > 0).collect(Collectors.toList());
    }
}
