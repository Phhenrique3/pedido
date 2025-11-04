package com.example.pedido.controller;

import com.example.pedido.service.PedidoService;
import com.example.pedido.metrics.PedidoMetrics;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
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

    // Criar pedido
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

    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<?> listarPedidos() {
        try {
            List<Map<String, Object>> pedidos = pedidoService.listarTodosPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
        }
    }

    // Pegar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> pegarPedido(@PathVariable String id) {
        try {
            Map<String, Object> pedido = pedidoService.pegarPedidoPorId(id);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Pedido não encontrado"));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
        }
    }

    // Atualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable String id,
                                             @RequestBody @NotNull Map<String, Object> body) {
        try {
            boolean atualizado = pedidoService.atualizarPedido(id, body);
            if (atualizado) {
                return ResponseEntity.ok(Map.of("message", "Pedido atualizado com sucesso"));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Pedido não encontrado"));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
        }
    }

    // Deletar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPedido(@PathVariable String id) {
        try {
            boolean deletado = pedidoService.deletarPedido(id);
            if (deletado) {
                return ResponseEntity.ok(Map.of("message", "Pedido deletado com sucesso"));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Pedido não encontrado"));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
        }
    }

    // Ping para teste
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
