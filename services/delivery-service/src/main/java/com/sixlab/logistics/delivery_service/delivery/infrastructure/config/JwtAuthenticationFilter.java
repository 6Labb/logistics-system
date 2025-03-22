package com.sixlab.logistics.delivery_service.delivery.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



/**
 *  Authorization 헤더에서 JWT를 가져와 검증함
 *  유효한 JWT면 Spring Security의 SecurityContext에 인증 정보를 저장하여, 인증된 상태로 API 요청을 수행하도록 함
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 헤더 확인용 로그 추가
        System.out.println("🚀 Backend 요청 헤더: " + request.getHeaderNames());
        System.out.println("🚀 Backend Authorization 헤더: " + request.getHeader("Authorization"));

        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        logger.error("Not Found Token");
        return null;
    }


}