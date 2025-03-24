package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.UserClientResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service",configuration = FeignClientInterceptor.class)
public interface UserClient {
    @GetMapping("/v1/{id}")
    ResponseEntity<ApiResponseDto<UserClientResponseDto>> getUser2(@PathVariable("id") Long id); //이거 권한걸림
    
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDto<UserClientResponseDto>> getUser(@PathVariable(name = "id") Long userId,@RequestHeader("X-User-Id") String userIdHeader,@RequestHeader("X-User-Role") String userRoleHeader);
    //2차시도
}
