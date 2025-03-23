package com.sixlab.logistics.company_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;

/**
 *  JWT가 유효한지 검증
 *  토큰에서 사용자 정보 추출
 */
@Component
public class JwtTokenProvider {

    //@Value("${service.jwt.secret-key}") // 🔥 Auth 서비스와 동일한 환경변수 사용
    private String SECRET_KEY = "401b09eab3c013d4ca54922bb802bec8fd5318192b0a75f201d8b3727429080fb337591abd3e44453b954555b7a0812e1081c39b740293f765eae731f5a65ed1";

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY)); // Auth 서비스와 동일한 방식으로 변경
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("🚀 JWT 검증 시작: " + token);

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

            System.out.println("✅ JWT 검증 성공!");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("🚨 JWT 검증 실패: " + e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String userId = claims.get("userId", String.class);
        String role = claims.get("role", String.class);

        if (userId == null || role == null) {
            System.out.println("🚨 getAuthentication 실패 - userId 또는 role이 null임!");
            return null;
        }

        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role; //
        }
        System.out.println("✅ getAuthentication - userId: " + userId + ", role: " + role);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);

        return new UsernamePasswordAuthenticationToken(userId, token, authorities);
    }

}
