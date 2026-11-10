package com.campuseats.metrica;

import com.campuseats.modelo.LineaPedido;

import java.time.Duration;
import java.util.*;

/** Strategy (metrica propia): minutos entre PAGADO y LISTO_RETIRO, hora pico (12-13:59) vs resto. */
public class TiempoPreparacion implements Metrica {
    public String nombre() { return "Tiempo de preparacion"; }

    public Object calcular(List<LineaPedido> datos) {
        Map<String, LineaPedido> pedidos = new LinkedHashMap<>();
        for (LineaPedido l : datos) pedidos.putIfAbsent(l.numeroPedido(), l);
        double sumaPico = 0, sumaResto = 0;
        int nPico = 0, nResto = 0;
        for (LineaPedido l : pedidos.values()) {
            if (l.fechaPago() == null || l.fechaListo() == null) continue;
            double min = Duration.between(l.fechaPago(), l.fechaListo()).toSeconds() / 60.0;
            int h = l.fechaPago().getHour();
            if (h >= 12 && h < 14) { sumaPico += min; nPico++; } else { sumaResto += min; nResto++; }
        }
        return String.format("  %.1f min entre las 12:00 y las 13:59 (%d pedidos)%n  %.1f min el resto del dia (%d pedidos)",
                nPico == 0 ? 0 : sumaPico / nPico, nPico, nResto == 0 ? 0 : sumaResto / nResto, nResto);
    }
}
