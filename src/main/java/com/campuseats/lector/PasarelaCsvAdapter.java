package com.campuseats.lector;

import com.campuseats.modelo.Pago;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter: traduce pagos_pasarela.csv (separado por ';', coma decimal, fecha dd/MM/yyyy HH:mm:ss).
 * Columnas: REFERENCIA;MONTO;ESTADO_TX;COD_AUTORIZACION;MOTIVO_RECHAZO;FECHA_HORA
 */
public class PasarelaCsvAdapter extends FuenteDatos<Pago> {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public PasarelaCsvAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    public List<Pago> leer() {
        List<Pago> pagos = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(ruta);
            for (int i = 1; i < lineas.size(); i++) {
                String[] c = lineas.get(i).split(";", -1);
                if (c.length >= 6) {
                    pagos.add(new Pago(
                            c[0].trim(),
                            Double.parseDouble(c[1].replace(",", ".").trim()),
                            c[2].trim(),
                            LocalDateTime.parse(c[5].trim(), FORMATO)));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo CSV de pasarela: " + e.getMessage());
        }
        return pagos;
    }
}
