package com.example.pedido.metrics;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PedidoMetrics {

    private final Counter pedidosCriados;
    private final DistributionSummary valorPedidos;
    private final Counter pedidosFalha;

    public PedidoMetrics(MeterRegistry registry) {
        this.pedidosCriados = Counter.builder("pedidos_criados_total")
                .description("Total de pedidos criados")
                .register(registry);

        this.valorPedidos = DistributionSummary.builder("pedidos_valor")
                .description("Distribuição dos valores dos pedidos")
                .baseUnit("BRL")
                .register(registry);

        this.pedidosFalha = Counter.builder("pedidos_falha_total")
                .description("Pedidos que falharam no processamento")
                .register(registry);
    }

    public void registrarPedido(BigDecimal valor) {
        pedidosCriados.increment();
        valorPedidos.record(valor.doubleValue());
    }

    public void registrarFalha() {
        pedidosFalha.increment();
    }
}
