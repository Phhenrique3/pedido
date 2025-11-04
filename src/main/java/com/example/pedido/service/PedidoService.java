package com.example.pedido.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    public String processarPedido(Map<String, Object> payload) throws Exception {
        // Simula latência e falha aleatória
        int delay = ThreadLocalRandom.current().nextInt(20, 400);
        Thread.sleep(delay);

        if (ThreadLocalRandom.current().nextInt(0, 100) < 8) { // 8% falha
            log.error("Falha simulada no processamento do pedido, payload={}", payload);
            throw new RuntimeException("Erro ao processar pedido (simulação)");
        }

        String id = UUID.randomUUID().toString();
        log.info("Pedido processado id={}, payload={}", id, payload);
        return id;
    }
}
