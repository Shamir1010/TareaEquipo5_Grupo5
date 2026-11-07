package com.campuseats.modelo;

import java.time.LocalDateTime;

public record LineaPedido(
        String numeroPedido,
        String platoId,
        int cantidad,
        double totalPedido,
        String estadoActual,
        LocalDateTime fechaHora
        ){

    public boolean estaPagado() {
        return "Pagado".equalsIgnoreCase(estadoActual)
                || "En preparacion".equalsIgnoreCase(estadoActual)
                || "Listo para retiro".equalsIgnoreCase(estadoActual)
                || "Entregado".equalsIgnoreCase(estadoActual);
    }
}
