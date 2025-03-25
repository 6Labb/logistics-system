package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.DeliveryClientResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "delivery-service",configuration = FeignClientInterceptor.class)
public interface DeliveryClient {


    @GetMapping("/deliveries/{id}")
    ResponseEntity<ApiResponseDto<DeliveryClientResponseDto>> getDelivery(@PathVariable UUID id);




}
