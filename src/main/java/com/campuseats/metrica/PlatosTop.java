package com.campuseats.metrica;

import com.campuseats.modelo.LineaPedido;

import java.util.*;
import java.util.stream.Collectors;

/** Strategy: los 5 platos mas vendidos (suma de unidades, no de filas). */
public class PlatosTop implements Metrica {
    public String nombre() { return "Platos mas vendidos (Top 5)"; }

    public Object calcular(List<LineaPedido> datos) {
        Map<String, Integer> unidades = new HashMap<>();
        for (LineaPedido l : datos) unidades.merge(l.nombrePlato(), l.cantidad(), Integer::sum);
        List<Map.Entry<String, Integer>> top = unidades.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> e : top) {
            sb.append(String.format("  %d. %s, %d unidades%n", i++, e.getKey(), e.getValue()));
        }
        return sb.toString().stripTrailing();
    }
}
