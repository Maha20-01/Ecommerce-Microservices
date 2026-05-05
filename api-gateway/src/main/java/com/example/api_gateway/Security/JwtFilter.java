package com.example.api_gateway.Security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("Incoming request: " + path);

        // ✅ Public endpoints
        if (path.equals("/users/login") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                (path.equals("/users") &&exchange.getRequest().getMethod().name().equals("POST"))) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String role = jwtUtil.getRole(token);
        String email = jwtUtil.getEmail(token);

        System.out.println("JWT VALIDATED for: " + email);

        // 🔥 Role-based check
        if (path.contains("/users/delete") && !"ADMIN".equals(role)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // ✅ Pass user info to downstream
        exchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header("X-User-Email", email)
                        .header("X-User-Role", role)
                        .build())
                .build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}