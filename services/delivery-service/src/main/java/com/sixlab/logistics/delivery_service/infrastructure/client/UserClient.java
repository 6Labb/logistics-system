package com.sixlab.logistics.delivery_service.infrastructure.client;

import com.sixlab.logistics.delivery_service.infrastructure.client.dto.HubTotalRouteResponseDto;
import com.sixlab.logistics.delivery_service.infrastructure.client.dto.UserDeliveryAgentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/delivery-agent")
    List<UserDeliveryAgentDto> getDeliveryAgentsByHub(@RequestParam("hubId") UUID hubId);

}
