package com.sixlab.logistics.slack_ai_service.Messenger.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiApiClientConfig {
    @Value("${gemini.key}")
    private String geminiApiKey;
    @Bean
    public RequestInterceptor geminiRequestInterceptor() {
        return requestTemplate -> {
            if ("geminiApiClient".equals(requestTemplate.feignTarget().name())) {
                requestTemplate.query("key", geminiApiKey);
            }
        };
    }
}
