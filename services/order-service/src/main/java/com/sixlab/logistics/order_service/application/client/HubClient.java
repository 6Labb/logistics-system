package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

//@FeignClient(name="hub-service")
//public interface HubClient {
//    // hub 조회 (param 은 id)
//    @GetMapping("/hubs/{id}")
//    ResponseEntity<HubResponseDto> getHubById(@PathVariable UUID id);
//}