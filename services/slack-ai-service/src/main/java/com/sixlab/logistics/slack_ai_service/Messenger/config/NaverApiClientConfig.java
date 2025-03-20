package com.sixlab.logistics.slack_ai_service.Messenger.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverApiClientConfig {

    @Value("${naver.key.id}")
    private String naverApiKeyId;

    @Value("${naver.api.key}")
    private String naverApiKey;

    @Bean
    public RequestInterceptor naverRequestInterceptor() {
        return requestTemplate -> {
            if ("naverApiClient".equals(requestTemplate.feignTarget().name())) {
                requestTemplate.header("x-ncp-apigw-api-key-id", naverApiKeyId);
                requestTemplate.header("x-ncp-apigw-api-key", naverApiKey);
            }
        };
    }
}