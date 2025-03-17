package com.sixlab.logistics.gateway_server.application.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    private final WhitelistProperties whitelistProperties;

    public LocalJwtAuthenticationFilter(WhitelistProperties whitelistProperties) {
        this.whitelistProperties = whitelistProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);
        Claims claims = extractClaims(token);
        if (token == null || claims == null) {
            return unauthorizedResponse(exchange);
        }

        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);

        if (username == null || role == null) {
            return unauthorizedResponse(exchange);

        }

        log.info("💚 username {} role {}", username, role);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Username", username)
                .header("X-User-Role", role)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isWhitelisted(String path) {
        return whitelistProperties.getPaths().stream().anyMatch(path::startsWith);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

    private Claims extractClaims(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                log.warn("token expired");
                return null;
            }

            return claims;
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            return null;
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
