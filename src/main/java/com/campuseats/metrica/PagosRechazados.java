package com.campuseats.metrica;

import com.campuseats.modelo.LineaPedido;
import com.campuseats.modelo.Pago;
import com.campuseats.repositorio.RepositorioAnalitica;

import java.time.LocalDateTime;
import java.util.List;

/** Strategy (metrica propia): porcentaje de transacciones de la pasarela que fueron RECHAZADA. */
public class PagosRechazados implements Metrica {

    private final RepositorioAnalitica repositorio;
    private final LocalDateTime desde;
    private final LocalDateTime hasta;

    public PagosRechazados(RepositorioAnalitica repositorio, LocalDateTime desde, LocalDateTime hasta) {
        this.repositorio = repositorio;
        this.desde = desde;
        this.hasta = hasta;
    }

    public String nombre() { return "Pagos rechazados"; }

    public Object calcular(List<LineaPedido> datos) {
        List<Pago> pagos = repositorio.pagosEntre(desde, hasta);
        if (pagos.isEmpty()) return "  Sin datos";
        long rechazados = pagos.stream().filter(p -> "RECHAZADA".equalsIgnoreCase(p.estado())).count();
        return String.format("  %d de %d transacciones (%.1f%%)", rechazados, pagos.size(), rechazados * 100.0 / pagos.size());
    }
}