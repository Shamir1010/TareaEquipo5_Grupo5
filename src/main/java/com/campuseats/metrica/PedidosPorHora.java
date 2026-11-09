package com.campuseats.metrica;

import com.campuseats.modelo.LineaPedido;

import java.util.*;
import java.util.stream.Collectors;

/** Strategy: pedidos por hora (hora del pago). Cuenta pedidos distintos, no lineas. */
public class PedidosPorHora implements Metrica {
    public String nombre() { return "Pedidos por hora"; }

    public Object calcular(List<LineaPedido> datos) {
        Map<String, Integer> porPedido = new HashMap<>();   // pedido -> hora
        Map<String, Integer> horaDePedido = new HashMap<>();
        for (LineaPedido l : datos) {
            if (l.fechaPago() != null) horaDePedido.putIfAbsent(l.numeroPedido(), l.fechaPago().getHour());
        }
        Map<Integer, Long> porHora = horaDePedido.values().stream()
                .collect(Collectors.groupingBy(h -> h, TreeMap::new, Collectors.counting()));
        List<Map.Entry<Integer, Long>> orden = porHora.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed()).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        if (!orden.isEmpty()) {
            sb.append(String.format("  Hora pico: %02d:00 con %d pedidos. Le siguen", orden.get(0).getKey(), orden.get(0).getValue()));
            for (int i = 1; i < Math.min(3, orden.size()); i++) {
                sb.append(String.format(" %02d:00 (%d)%s", orden.get(i).getKey(), orden.get(i).getValue(), i < 2 ? "," : ""));
            }
            sb.append(".\n  Detalle: ");
            porHora.forEach((h, c) -> sb.append(String.format("%02d:00=%d  ", h, c)));
        }
        return sb.toString().stripTrailing();
    }
}
