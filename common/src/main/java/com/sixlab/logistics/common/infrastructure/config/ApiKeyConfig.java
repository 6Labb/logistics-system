package com.sixlab.logistics.common.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeyConfig {

    @Value("${gemini.key}")
    private String geminiApiKey;

    @Value("${naver.key.id}")
    private String naverApiKeyId;

    @Value("${naver.api.key}")
    private String naverApiKey;

    @Value("${slack.token}")
    private String token;

    

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String clientName = requestTemplate.feignTarget().name();
            if("geminiApiKey".equals(clientName)) {
                requestTemplate.query("key", geminiApiKey);
            }
            if("slackApiClient".equals(clientName)) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
            if("naverApiKeyId".equals(clientName)) {
                requestTemplate.header("x-ncp-apigw-api-key-id" + naverApiKeyId);
            }
            if("naverApiKey".equals(clientName)) {
                requestTemplate.header("x-ncp-apigw-api-key", naverApiKey);
            }
        };
    }
}