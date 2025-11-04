package com.example.pedido.controller;

import com.example.pedido.service.PedidoService;
import com.example.pedido.metrics.PedidoMetrics;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@Validated
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMetrics metrics;

    public PedidoController(PedidoService pedidoService, PedidoMetrics metrics) {
        this.pedidoService = pedidoService;
        this.metrics = metrics;
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody @NotNull Map<String, Object> body) {
        try {
            BigDecimal valor = new BigDecimal(body.get("valor").toString());
            String pedidoId = pedidoService.processarPedido(body);

            metrics.registrarPedido(valor);
            return ResponseEntity.ok(Map.of("pedidoId", pedidoId));
        } catch (Exception ex) {
            metrics.registrarFalha();
            return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
