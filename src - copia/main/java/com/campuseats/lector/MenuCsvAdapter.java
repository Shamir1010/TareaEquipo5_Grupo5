package com.campuseats.lector;

import com.campuseats.modelo.Plato;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class MenuCsvAdapter extends FuenteDatos<Plato> {
    public MenuCsvAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    public List<Plato> leer() {
        List<Plato> menu = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(ruta);
            for (int i = 1; i < lineas.size(); i++) {
                String[] columnas = lineas.get(i).split(",");
                if (columnas.length >= 5) {
                    menu.add(new Plato(
                            columnas[0].trim(),
                            columnas[1].trim(),
                            columnas[2].trim(),
                            Double.parseDouble(columnas[3].trim()),
                            columnas[4].trim()
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo CSV del menu: " + e.getMessage());
        }
        return menu;
    }
}
