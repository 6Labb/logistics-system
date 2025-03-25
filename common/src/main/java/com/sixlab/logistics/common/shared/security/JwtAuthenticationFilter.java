package com.sixlab.logistics.common.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 *  Authorization 헤더에서 JWT를 가져와 검증함
 *  유효한 JWT면 Spring Security의 SecurityContext에 인증 정보를 저장하여, 인증된 상태로 API 요청을 수행하도록 함
 */

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

        String requestURI = request.getRequestURI();

        // 로그인 & 회원가입 요청은 JWT 필터 적용 제외!
        if (requestURI.equals("/auth/signIn") || requestURI.equals("/users/signUp")
                || requestURI.startsWith("/api-docs-user-service")
                || requestURI.startsWith("/api-docs-hub-service")
                || requestURI.startsWith("/api-docs-company-service")
                || requestURI.startsWith("/api-docs-product-service")
                || requestURI.startsWith("/api-docs-order-service")
                || requestURI.startsWith("/api-docs-delivery-service")
                || requestURI.startsWith("/api-docs-slack-ai-service")
                || requestURI.startsWith("/swagger-ui")
                || requestURI.startsWith("/v3/api-docs")
                || requestURI.startsWith("/api-docs")
                || requestURI.contains("swagger")
                || requestURI.contains("api-docs")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // 요청 헤더 확인용 로그 추가
        System.out.println("🚀 Backend 요청 헤더: " + request.getHeaderNames());
        System.out.println("🚀 Backend Authorization 헤더: " + request.getHeader("Authorization"));

        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            if (auth == null) { // ✅ auth가 null이면 401 Unauthorized 반환
                System.out.println("🚨 getAuthentication() 실패 - SecurityContext에 저장 안 됨!");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            System.out.println("✅ SecurityContext 저장된 사용자: " + auth.getName());
            System.out.println("✅ SecurityContext 저장된 권한: " + auth.getAuthorities());
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken == null) {
            System.out.println("🚨 Authorization 헤더가 존재하지 않음!");
            return null;
        }

        System.out.println("🚀 요청에서 추출된 Authorization 헤더: " + bearerToken);

        if (bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(7);
            System.out.println("✅ 추출된 JWT: " + token);
            return token;
        }

        System.out.println("🚨 Authorization 헤더 형식이 올바르지 않음!");
        return null;
    }


}
