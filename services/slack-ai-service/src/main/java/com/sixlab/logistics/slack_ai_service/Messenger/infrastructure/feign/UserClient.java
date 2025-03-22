package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.UserClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userClient")
public interface UserClient {
    @GetMapping("/v1/{id}")
    ApiResponse<UserClientResponseDto> getUser2(@PathVariable("id") String id);
}
