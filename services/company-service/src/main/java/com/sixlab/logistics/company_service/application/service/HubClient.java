package com.sixlab.logistics.company_service.application.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service", url = "http://hub-service")
public interface HubClient {

//    @GetMapping("/api/hubs/{hubId}")  // Hub API의 URL
//    ExternalHubResponseDto getHub(@PathVariable("hubId") UUID hubId);  // HubId를 path로 받아서 호출
}