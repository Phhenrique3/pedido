package com.example.pedido.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    // Armazenamento em memória seguro para threads
    private final Map<String, Map<String, Object>> pedidos = new ConcurrentHashMap<>();

    public String processarPedido(Map<String, Object> payload) throws Exception {
        // Simula latência
        int delay = ThreadLocalRandom.current().nextInt(20, 400);
        Thread.sleep(delay);

        // Simula falha aleatória
        if (ThreadLocalRandom.current().nextInt(0, 100) < 8) { // 8% de falha
            log.error("Falha simulada no processamento do pedido, payload={}", payload);
            throw new RuntimeException("Erro ao processar pedido (simulação)");
        }

        // Gera UUID
        String id = UUID.randomUUID().toString();
        payload.put("id", id);

        // Armazena pedido
        pedidos.put(id, payload);

        log.info("Pedido processado id={}, payload={}", id, payload);
        return id;
    }

    public List<Map<String, Object>> listarTodosPedidos() {
        return new ArrayList<>(pedidos.values());
    }

    public Map<String, Object> pegarPedidoPorId(String id) {
        return pedidos.get(id);
    }

    public boolean atualizarPedido(String id, Map<String, Object> novoPedido) {
        if (!pedidos.containsKey(id)) return false;
        novoPedido.put("id", id);
        pedidos.put(id, novoPedido);
        log.info("Pedido atualizado id={}, payload={}", id, novoPedido);
        return true;
    }

    public boolean deletarPedido(String id) {
        if (pedidos.remove(id) != null) {
            log.info("Pedido deletado id={}", id);
            return true;
        }
        return false;
    }
}
