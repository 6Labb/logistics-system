package com.sixlab.logistics.slack_ai_service.Messenger.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApiConfig {
    @Value("${slack.token}")
    private String token;

    @Bean
    public RequestInterceptor slackRequestInterceptor() {
        return requestTemplate -> {
            if ("slackApiClient".equals(requestTemplate.feignTarget().name())) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
