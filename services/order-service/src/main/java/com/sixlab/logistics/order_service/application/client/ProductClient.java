package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="product-service")
public interface ProductClient {
    // product 조회 (param 은 productId)
    // 1. RequestMapping("/products") 있는지 확인할 것
    @GetMapping("/products/{productId}")
    ApiResponse<GetProductResponseDto> getProductById(@PathVariable UUID productId);
}
