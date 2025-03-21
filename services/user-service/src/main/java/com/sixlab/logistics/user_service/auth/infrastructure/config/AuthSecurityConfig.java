package com.sixlab.logistics.user_service.auth.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AuthSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // SecurityFilterChain 빈을 정의합니다. 이 메서드는 Spring Security의 보안 필터 체인을 구성합니다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호를 비활성화합니다. CSRF 보호는 주로 브라우저 클라이언트를 대상으로 하는 공격을 방지하기 위해 사용됩니다.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize // 요청에 대한 접근 권한을 설정합니다.
                        // /auth/signIn 경로에 대한 접근을 허용합니다. 이 경로는 인증 없이 접근할 수 있습니다.
                        .requestMatchers("/auth/signIn").permitAll()
                        .requestMatchers("/users/signUp").permitAll()
                        // 그 외의 모든 요청은 인증이 필요합니다.
                        .anyRequest().authenticated()
                )
                // 세션 관리 정책을 정의합니다. 여기서는 세션을 사용하지 않도록 STATELESS로 설정합니다. 지금은 jwt 사용할 것이기 때문.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 🔥 JWT 필터 등록! (UsernamePasswordAuthenticationFilter 전에 실행)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        // 설정된 보안 필터 체인을 반환합니다.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        return SecurityContextHolder.getContextHolderStrategy();
    }
}
