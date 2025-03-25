package com.sixlab.logistics.product_service.application.client;

import com.sixlab.logistics.product_service.application.dto.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {

    @GetMapping("/api/hubs/{hubId}")  // Hub API의 URL -> Hub 서비스에서 hubId 검증 API가 필요
    HubResponseDto getHubById(@PathVariable("hubId") UUID hubId);  // HubId를 path로 받아서 호출
}