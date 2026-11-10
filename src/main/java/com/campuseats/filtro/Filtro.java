package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;
import java.util.List;

/** Pipeline & Filters: un filtro aplica UNA sola regla de limpieza. */
public interface Filtro {
    String nombre();
    List<LineaPedido> aplicar(List<LineaPedido> lineas);
}
