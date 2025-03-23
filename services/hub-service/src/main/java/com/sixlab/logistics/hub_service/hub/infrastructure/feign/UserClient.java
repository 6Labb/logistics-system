package com.sixlab.logistics.hub_service.hub.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {

//    @GetMapping("/users/{id}")
//    UserResponseDto getUser(@PathVariable(name = "id") Long userId,
//                            @RequestHeader("Authorization") String token
//    );

    @GetMapping("/users/{id}")
    UserResponseDto getUser(
            @PathVariable(name = "id") Long userId,
            @RequestHeader("X-User-Id") String userIdHeader,
            @RequestHeader("X-User-Role") String userRoleHeader
    );

}
