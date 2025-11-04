package com.example.pedido.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String requestId = req.getHeader("X-Request-ID");
            if (requestId == null || requestId.isBlank()) {
                requestId = UUID.randomUUID().toString();
            }
            MDC.put("requestId", requestId);

            String pedidoId = req.getHeader("X-Pedido-Id");
            if (pedidoId != null) MDC.put("pedidoId", pedidoId);

            String userId = req.getHeader("X-User-Id");
            if (userId != null) MDC.put("userId", userId);

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
