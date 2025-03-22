package com.sixlab.logistics.slack_ai_service.Messenger.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() instanceof String token) {
            String userId = authentication.getName();
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");

            String clientName = requestTemplate.feignTarget().name();

            // 외부 api 헤더 전달 X
            if (!(
                    "slackApiClient".equals(clientName) ||
                    "geminiApiClient".equals(clientName) ||
                    "naverApiClient".equals(clientName)
            )) {
                requestTemplate.header("Authorization", "Bearer " + token);
                requestTemplate.header("X-Hub-User", userId);
                requestTemplate.header("X-Hub-Role", role);
                System.out.println("🚀 일반 FeignClientInterceptor 적용됨: " + clientName);
            }
        }
    }
}
