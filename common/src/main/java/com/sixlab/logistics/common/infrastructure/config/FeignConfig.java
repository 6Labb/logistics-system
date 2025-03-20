package com.sixlab.logistics.common.infrastructure.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    //feign retryer 비활성화
    @Bean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    // 모든 요청/응답 로깅
    //none(기본값) BASIC(요청메서드,URL,응답상태,실행시간) HEADERS(응답 요청헤더 포함) FULL(모든정보)
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}