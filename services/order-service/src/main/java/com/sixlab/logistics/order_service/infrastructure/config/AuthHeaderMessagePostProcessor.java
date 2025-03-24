package com.sixlab.logistics.order_service.infrastructure.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHeaderMessagePostProcessor implements MessagePostProcessor {
    @Override
    public Message postProcessMessage(Message message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() instanceof String token) {
            message.getMessageProperties().setHeader("Authorization", "Bearer " + token);
            message.getMessageProperties().setHeader("X-Hub-User", authentication.getName()); // user ID

            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");
            message.getMessageProperties().setHeader("X-Hub-Role", role); // user role
        }
        return message;
    }
}
