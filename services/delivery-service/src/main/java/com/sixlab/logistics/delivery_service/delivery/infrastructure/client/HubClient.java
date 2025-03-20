package com.sixlab.logistics.delivery_service.delivery.infrastructure.client;

import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {


    @GetMapping("/hubs/routes/{hubRouteId}")
    HubRouteResponseDto getHubRouteId(
            @RequestParam UUID fromHubId,
            @RequestParam UUID toHubId);

}
