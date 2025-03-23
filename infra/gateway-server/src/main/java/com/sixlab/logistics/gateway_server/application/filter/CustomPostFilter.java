package com.sixlab.logistics.gateway_server.application.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class CustomPostFilter implements GlobalFilter, Ordered {

    private static final Logger logger = Logger.getLogger(CustomPostFilter.class.getName());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpRequest request = exchange.getRequest();

            // Backend로 가기 전 Authorization 헤더 확인
            System.out.println("🚀 PostFilter - Backend 요청 URL: " + request.getURI());
            System.out.println("🚀 PostFilter - Authorization 헤더: " + request.getHeaders().getFirst("Authorization"));

            ServerHttpResponse response = exchange.getResponse();
            logger.info("Post Filter : Response status code is" + response.getStatusCode());

//             Authorization 헤더 확인
//            String authorizationHeader = response.getHeaders().getFirst("Authorization");
//            System.out.println("🚀 PostFilter - 응답 Authorization 헤더: " + authorizationHeader);

            // 응답이 반환된 후에 Authorization 헤더를 설정
            response.getHeaders().add("Authorization", request.getHeaders().getFirst("Authorization"));
            // 응답 Authorization 헤더 확인
            String authorizationHeader = response.getHeaders().getFirst("Authorization");
            System.out.println("🚀 PostFilter - 응답 Authorization 헤더: " + authorizationHeader);

//            if (authorizationHeader != null) {
//                // Authorization 헤더를 강제로 유지하도록 설정
//                response.getHeaders().set("Authorization", authorizationHeader);
//            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
