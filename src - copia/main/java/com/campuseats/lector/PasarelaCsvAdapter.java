package com.campuseats.lector;

import com.campuseats.modelo.Pago;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class PasarelaCsvAdapter extends FuenteDatos<Pago> {
    public PasarelaCsvAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    public List<Pago> leer() {
        List<Pago> pagos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            List<String> lineas = Files.readAllLines(ruta);
            for (int i = 1; i < lineas.size(); i++) {
                String[] columnas = lineas.get(i).split(";");
                if (columnas.length >= 4) {
                    String idPedido = columnas[0].trim();
                    double monto = Double.parseDouble(columnas[1].replace(",", ".").trim());
                    String estado = columnas[2].trim();
                    LocalDateTime fecha = LocalDate.parse(columnas[3].trim(), formatter).atStartOfDay();
                    pagos.add(new Pago(idPedido, monto, estado, fecha));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo CSV de pasarela: " + e.getMessage());
        }
        return pagos;
    }
}
