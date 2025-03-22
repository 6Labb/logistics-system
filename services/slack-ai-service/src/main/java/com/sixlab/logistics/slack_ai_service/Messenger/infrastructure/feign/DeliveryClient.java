package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.DeliveryClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "deliveryClient")
public interface DeliveryClient {


    @GetMapping("/deliveries/{id}")
    ApiResponse<DeliveryClientResponseDto> getDelivery(@PathVariable("id") UUID id);



}
