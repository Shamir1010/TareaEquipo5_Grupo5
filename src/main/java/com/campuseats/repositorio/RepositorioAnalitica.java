package com.campuseats.repositorio;

import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Pago;
import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioAnalitica {
    void guardarLineas(List<LineaPedido> lineas);
    void guardarPagos(List<Pago> pagos);
    List<LineaPedido> lineasEntre(LocalDateTime desde, LocalDateTime hasta);
    List<Pago> pagosEntre(LocalDateTime desde, LocalDateTime hasta);
    List<LineaPedido> obtenerTodasLineas();
}