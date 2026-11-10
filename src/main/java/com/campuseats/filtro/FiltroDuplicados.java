package com.campuseats.filtro;

import com.campuseats.modelo.LineaPedido;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Regla: cada pedido tiene un numero unico. Un pedido repetido se cuenta solo una vez. */
public class FiltroDuplicados implements Filtro {
    public String nombre() { return "Pedidos repetidos"; }

    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        // En un pedido no se repite el mismo plato, asi que (pedido, plato) identifica una linea.
        Set<String> vistas = new HashSet<>();
        return lineas.stream()
                .filter(l -> vistas.add(l.numeroPedido() + "|" + l.platoId()))
                .collect(Collectors.toList());
    }
}
