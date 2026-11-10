package com.campuseats.modelo;

import java.time.LocalDateTime;

/**
 * Una linea = un plato dentro de un pedido.
 * fechaHora  : cuando se registro el pedido (se usa para filtrar por periodo).
 * fechaPago  : primera vez que el pedido paso por PAGADO (null si nunca se pago).
 * fechaListo : cuando quedo en LISTO_RETIRO (null si no llego).
 * nombrePlato: se completa en el pipeline a partir del menu (null al leer).
 */
public record LineaPedido(
        String numeroPedido,
        String platoId,
        String nombrePlato,
        int cantidad,
        double precioUnitario,
        double totalPedido,
        String estadoActual,
        LocalDateTime fechaHora,
        LocalDateTime fechaPago,
        LocalDateTime fechaListo
) {

    /** Un pedido cuenta como venta solo si alguna vez paso por el estado PAGADO. */
    public boolean estaPagado() {
        return fechaPago != null;
    }

    public LineaPedido conNombrePlato(String nombre) {
        return new LineaPedido(numeroPedido, platoId, nombre, cantidad, precioUnitario,
                totalPedido, estadoActual, fechaHora, fechaPago, fechaListo);
    }
}
