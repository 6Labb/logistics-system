package com.sixlab.logistics.delivery_service.infrastructure.client;

import com.sixlab.logistics.delivery_service.infrastructure.client.dto.HubTotalRouteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {

    @GetMapping("/hubs/{hubTotalRouteId}")
    HubTotalRouteResponseDto getHubTotalRouteId(@RequestParam UUID fromHubId, @RequestParam UUID toHubId);

}
