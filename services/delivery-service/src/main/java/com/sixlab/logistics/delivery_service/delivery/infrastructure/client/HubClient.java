package com.sixlab.logistics.delivery_service.delivery.infrastructure.client;

import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {


    // 허브 이동관리 id 조회
    @GetMapping("/hubs/routes/{hubRouteId}")
    HubRouteResponseDto getHubRouteId(
            @RequestParam UUID fromHubId,
            @RequestParam UUID toHubId);

    // user_id로 소속허브 id 조회
    @GetMapping("/hubs/managers/{userId}")
    HubManagerResponseDto getHubIdByUserId(@PathVariable Long userId);

}
