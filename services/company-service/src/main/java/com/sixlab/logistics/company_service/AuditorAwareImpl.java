package com.sixlab.logistics.company_service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
//public class AuditorAwareImpl implements AuditorAware<String> {
//    @Override
//    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//
//        return Optional.of(authentication.getName()); // 현재 로그인한 사용자의 username 반환
//    }
//}
