package com.campuseats.modelo;

import java.time.LocalDateTime;

public record Pago(
        String numeroPedido,
        double monto,
        String estado, // APROBADA, RECHAZADA, SIN_RESPUESTA
        LocalDateTime fecha
        ) {

}
