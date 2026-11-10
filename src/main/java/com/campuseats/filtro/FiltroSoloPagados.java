package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;
import java.util.List;
import java.util.stream.Collectors;

/** Regla: solo cuentan como venta los pedidos que ya pasaron por "Pagado". */
public class FiltroSoloPagados implements Filtro {
    public String nombre() { return "Pedidos que no fueron pagados"; }

    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream().filter(LineaPedido::estaPagado).collect(Collectors.toList());
    }
}
