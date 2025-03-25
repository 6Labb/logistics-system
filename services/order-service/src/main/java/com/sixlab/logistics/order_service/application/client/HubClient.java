package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="hub-service")
public interface HubClient {
    // hubManager 조회 (param 은 userId)
    @GetMapping("/hubs/{id}")
    ResponseEntity<ApiResponseDto<HubResponseDto>> getHubById(@PathVariable UUID id);
    // ResponseEntity<TempDto> getHubById(@PathVariable UUID id);
}