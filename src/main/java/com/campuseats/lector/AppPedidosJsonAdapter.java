package com.campuseats.lector;

import com.campuseats.modelo.LineaPedido;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Adapter: traduce pedidos_app.json (un objeto por pedido) a lineas del modelo. */
public class AppPedidosJsonAdapter extends FuenteDatos<LineaPedido> {

    public AppPedidosJsonAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    public List<LineaPedido> leer() {
        List<LineaPedido> lineas = new ArrayList<>();
        try {
            JsonNode root = new ObjectMapper().readTree(ruta.toFile());
            for (JsonNode pedido : root) {
                String numero = pedido.get("numeroPedido").asText();
                double total = pedido.get("total").asDouble();
                String estado = pedido.get("estado").asText();
                LocalDateTime registrado = LocalDateTime.parse(pedido.get("registradoEn").asText());

                LocalDateTime pago = null;
                LocalDateTime listo = null;
                for (JsonNode h : pedido.get("historialEstados")) {
                    String e = h.get("estado").asText();
                    LocalDateTime f = LocalDateTime.parse(h.get("fechaHora").asText());
                    if (e.equals("PAGADO") && pago == null) pago = f;
                    if (e.equals("LISTO_RETIRO") && listo == null) listo = f;
                }

                for (JsonNode l : pedido.get("lineas")) {
                    lineas.add(new LineaPedido(numero, l.get("platoId").asText(), null,
                            l.get("cantidad").asInt(), l.get("precioUnitario").asDouble(),
                            total, estado, registrado, pago, listo));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo JSON de pedidos: " + e.getMessage());
        }
        return lineas;
    }
}
