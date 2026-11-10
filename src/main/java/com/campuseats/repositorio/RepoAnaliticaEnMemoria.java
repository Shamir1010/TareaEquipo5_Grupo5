package com.campuseats.repositorio;

import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Pago;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoAnaliticaEnMemoria implements RepositorioAnalitica {

    private final List<LineaPedido> lineas = new ArrayList<>();
    private final List<Pago> pagos = new ArrayList<>();

    @Override
    public void guardarLineas(List<LineaPedido> nuevasLineas) {
        if (nuevasLineas != null) {
            this.lineas.addAll(nuevasLineas);
        }
    }

    @Override
    public void guardarPagos(List<Pago> nuevosPagos) {
        if (nuevosPagos != null) {
            this.pagos.addAll(nuevosPagos);
        }
    }

    @Override
    public List<LineaPedido> lineasEntre(LocalDateTime desde, LocalDateTime hasta) {
        return lineas.stream()
                .filter(l -> l.fechaHora() != null 
                        && !l.fechaHora().isBefore(desde) 
                        && !l.fechaHora().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pago> pagosEntre(LocalDateTime desde, LocalDateTime hasta) {
        return pagos.stream()
                .filter(p -> p.fecha() != null 
                        && !p.fecha().isBefore(desde) 
                        && !p.fecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public List<LineaPedido> obtenerTodasLineas() {
        return new ArrayList<>(lineas);
    }
}