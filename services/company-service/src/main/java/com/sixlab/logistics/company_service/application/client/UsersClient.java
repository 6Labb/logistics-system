package com.sixlab.logistics.company_service.application.client;

import com.sixlab.logistics.company_service.application.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UsersClient {
    @GetMapping("/users/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);
}
