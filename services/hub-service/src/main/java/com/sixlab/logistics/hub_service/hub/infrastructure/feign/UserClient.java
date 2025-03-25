package com.sixlab.logistics.hub_service.hub.infrastructure.feign;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/{id}")
    ApiResponseDto<ExternalUserResponseDto> getUser(
            @PathVariable(name = "id") Long userId
    );

}
