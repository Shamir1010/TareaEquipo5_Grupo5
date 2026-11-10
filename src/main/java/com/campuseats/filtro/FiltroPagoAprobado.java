package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Pago;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Regla: un pedido pagado debe tener un pago APROBADA en la pasarela
 * y el monto cobrado debe ser igual al total del pedido.
 */
public class FiltroPagoAprobado implements Filtro {

    private final Map<String, Double> aprobados = new HashMap<>();

    public FiltroPagoAprobado(List<Pago> pagos) {
        for (Pago p : pagos) {
            if ("APROBADA".equalsIgnoreCase(p.estado())) {
                aprobados.put(p.numeroPedido(), p.monto());
            }
        }
    }

    public String nombre() { return "Pago no aprobado por la pasarela o monto distinto al total"; }

    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream()
                .filter(l -> aprobados.containsKey(l.numeroPedido())
                        && Math.abs(aprobados.get(l.numeroPedido()) - l.totalPedido()) < 0.005)
                .collect(Collectors.toList());
    }
}
