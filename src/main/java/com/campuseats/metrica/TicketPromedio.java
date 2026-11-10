package com.campuseats.metrica;

import com.campuseats.modelo.LineaPedido;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Strategy (metrica propia): ticket promedio = total vendido / pedidos distintos. */
public class TicketPromedio implements Metrica {
    public String nombre() { return "Ticket promedio"; }

    public Object calcular(List<LineaPedido> datos) {
        Map<String, Double> totales = new HashMap<>();
        for (LineaPedido l : datos) totales.putIfAbsent(l.numeroPedido(), l.totalPedido());
        if (totales.isEmpty()) return "  Sin datos";
        double prom = totales.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        return String.format("  %.2f dolares por pedido (%d pedidos)", prom, totales.size());
    }
}
