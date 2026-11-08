package com.campuseats.metrica;

import com.campuseats.modelo.LineaPedido;
import java.util.List;

public interface Metrica {
    String nombre();
    Object calcular(List<LineaPedido> datos);
}