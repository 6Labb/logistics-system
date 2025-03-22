package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="hub-service")
public interface HubManagerClient {
    // hub 조회 (param 은 hubId)
    @GetMapping("/hubs/{hubManagerId}")
    // ResponseDto 추후 추가 예정
    ApiResponse<?> getHubManagerById(@PathVariable UUID hubManagerId);
}