package com.sixlab.logistics.user_service.auth.application.service;


import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.exception.UnauthorizedAccessException;
import com.sixlab.logistics.user_service.auth.application.dto.LoginRequestDto;
import com.sixlab.logistics.user_service.user.domain.model.User;
import com.sixlab.logistics.user_service.user.domain.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.processing.RoundEnvironment;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

    /**
     * AuthService 생성자.
     * Base64 URL 인코딩된 비밀 키를 디코딩하여 HMAC-SHA 알고리즘에 적합한 SecretKey 객체를 생성합니다.
     *
     * @param secretKey Base64 URL 인코딩된 비밀 키
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${service.jwt.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    /**
     * 사용자 ID를 받아 JWT 액세스 토큰을 생성합니다.
     *
     * @param
     * @return 생성된 JWT 액세스 토큰
     */
    public String createAccessToken(User user) {
        return Jwts.builder()
                // 사용자 ID를 클레임으로 설정
                .claim("userId", user.getId().toString())
                .claim("role", user.getRole())
                // JWT 발행자를 설정
                .issuer(issuer)
                // JWT 발행 시간을 현재 시간으로 설정
                .issuedAt(new Date(System.currentTimeMillis()))
                // JWT 만료 시간을 설정
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                // SecretKey를 사용하여 HMAC-SHA512 알고리즘으로 서명
                .signWith(secretKey, Jwts.SIG.HS512)
                // JWT 문자열로 컴팩트하게 변환
                .compact();
    }

    public String authenticate(LoginRequestDto request) {
        // 사용자 조회
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        // 비밀번호 검증
        if ( user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }

        // JWT 생성 (userID + role)
        return createAccessToken(user);
    }
}
