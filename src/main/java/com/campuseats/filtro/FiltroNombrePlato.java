package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Plato;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Filtro de enriquecimiento (va al final): completa el nombre del plato desde el menu.
 * No descarta nada: un plato RETIRADO se conserva en el historial (CU-7.3) y el precio
 * de la linea no se toca (CU-7.2).
 */
public class FiltroNombrePlato implements Filtro {

    private final Map<String, String> nombres = new HashMap<>();

    public FiltroNombrePlato(List<Plato> menu) {
        for (Plato p : menu) nombres.put(p.id(), p.nombre());
    }

    public String nombre() { return "Completar nombre del plato"; }

    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream()
                .map(l -> l.conNombrePlato(nombres.getOrDefault(l.platoId(), "Plato " + l.platoId())))
                .collect(Collectors.toList());
    }
}
