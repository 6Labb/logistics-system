package com.sixlab.logistics.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing
public class JpaConfig {

//    @Bean
//    @Profile("!test")
//    public AuditorAware<String> auditorProvider() {
//        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return Optional.empty();
//            }
//            return Optional.ofNullable(authentication.getName());
//        };
//    }
}