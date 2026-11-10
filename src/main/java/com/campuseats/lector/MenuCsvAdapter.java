package com.campuseats.lector;

import com.campuseats.modelo.Plato;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Adapter: traduce menu_admin.csv (separado por coma) a objetos Plato. */
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
                String[] c = lineas.get(i).split(",");
                if (c.length >= 5) {
                    menu.add(new Plato(c[0].trim(), c[1].trim(), c[2].trim(),
                            Double.parseDouble(c[3].trim()), c[4].trim()));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo CSV del menu: " + e.getMessage());
        }
        return menu;
    }
}
