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
        ServerHttpRequest request = exchange.getRequest();

        // Gateway에서 JWT 검증 전 Authorization 헤더 확인
        System.out.println("LocalJwtAuthenticationFilter - 요청 URL: " + request.getURI());
        System.out.println("LocalJwtAuthenticationFilter - Authorization 헤더: " + request.getHeaders().getFirst("Authorization"));

        String path = exchange.getRequest().getURI().getPath();
        System.out.println("요청 path: " + path);
        if (isWhitelisted(path)) {
            System.out.println("whitelist 경로 - 토큰 없이 통과");
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);
        System.out.println("LocalJwtAuthenticationFilter - Extracted Token: " + token);

        Claims claims = extractClaims(token);
        if (token == null || claims == null) {
            return unauthorizedResponse(exchange);
        }

        String userId = claims.get("userId", String.class);
        String role = claims.get("role", String.class);

        if (userId == null || role == null) {
            return unauthorizedResponse(exchange);
        }

        log.info("userId {} role {}", userId, role);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                //.header("Authorization", exchange.getRequest().getHeaders().getFirst("Authorization"))
                .header("Authorization", "Bearer " + token)
                .header("X-User-Id", userId) // 사용자 ID 추가
                .header("X-User-Role", role) // 사용자 역할 추가
                .build();

        System.out.println("Gateway → Backend 요청 헤더");
        System.out.println("   Authorization: " + token);
        System.out.println("   X-User-Id: " + userId);
        System.out.println("   X-User-Role: " + role);


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
            log.info("extractClaims() 호출됨 - Token: {}", token);

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            log.info("JWT 파싱 성공! claims: {}", claims);

            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                log.warn("JWT 만료됨 Expiration: {}", expiration);
                return null;
            }

            return claims;
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage(), e);
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
