package com.campuseats.lector;
import com.campuseats.modelo.LineaPedido;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class AppPedidosJsonAdapter extends FuenteDatos<LineaPedido> {
    public AppPedidosJsonAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    public List<LineaPedido> leer() {
        List<LineaPedido> lineas = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(ruta.toFile());
            for (JsonNode pedido : root) {
                String numeroPedido = pedido.get("numero_pedido").asText();
                double total = pedido.get("total").asDouble();
                JsonNode historial = pedido.get("historial_estados");
                JsonNode ultimoEstado = historial.get(historial.size() - 1);
                String estadoStr = ultimoEstado.get("estado").asText();
                LocalDateTime fechaHora = LocalDateTime.parse(ultimoEstado.get("fecha_hora").asText(), DateTimeFormatter.ISO_DATE_TIME);

                JsonNode platos = pedido.get("platos");
                for (JsonNode plato : platos) {
                    String platoId = plato.get("id_plato").asText();
                    int cantidad = plato.get("cantidad").asInt();
                    lineas.add(new LineaPedido(numeroPedido, platoId, cantidad, total, estadoStr, fechaHora));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo JSON de pedidos: " + e.getMessage());
        }
        return lineas;
    }
}
