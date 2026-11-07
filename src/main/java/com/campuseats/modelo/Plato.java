package com.campuseats.modelo;

public record Plato(
        String id,
        String nombre,
        String categoria,
        double precio,
        String estado // DISPONIBLE, AGOTADO, RETIRADO
        ) {
}
