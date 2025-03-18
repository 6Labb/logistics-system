package com.sixlab.logistics.company_service.application.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient {

    // Hub 서비스에서 Hub 정보를 가져오는 API
//    @GetMapping("/api/hubs/{hubId}")  // Hub API의 URL
//    HubResponseDto getHubById(@PathVariable("hubId") UUID hubId);  // HubId를 path로 받아서 호출
}