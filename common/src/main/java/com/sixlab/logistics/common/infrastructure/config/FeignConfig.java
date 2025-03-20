//package com.sixlab.logistics.common.infrastructure.config;
//
//import feign.Logger;
//import feign.RequestInterceptor;
//import feign.Retryer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignConfig {
//
//    @Value("${gemini.key}")
//    private String geminiApiKey;
//
//    @Value("${naver.key.id}")
//    private String naverApiKeyId;
//
//    @Value("${naver.api.key}")
//    private String naverApiKey;
//
//    @Value("${slack.token}")
//    private String token;
//
//
//    //feign retryer 비활성화
//    @Bean
//    public Retryer feignRetryer() {
//        return Retryer.NEVER_RETRY;
//    }
//
//    // 모든 요청/응답 로깅
//    //none(기본값) BASIC(요청메서드,URL,응답상태,실행시간) HEADERS(응답 요청헤더 포함) FULL(모든정보)
//    @Bean
//    public Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
//
//    //인터셉터
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            String clientName = requestTemplate.feignTarget().name();
//            if("geminiApiClient".equals(clientName)) {
//                requestTemplate.query("key", geminiApiKey);
//            }
//            if("slackApiClient".equals(clientName)) {
//                requestTemplate.header("Authorization", "Bearer " + token);
//            }
//            if("naverApiClient".equals(clientName)) {
//                requestTemplate.header("x-ncp-apigw-api-key-id",naverApiKeyId);
//                requestTemplate.header("x-ncp-apigw-api-key", naverApiKey);
//            }
//            System.out.println(geminiApiKey+":"+token);
//        };
//    }
//}