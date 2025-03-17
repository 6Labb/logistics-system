package com.sixlab.logistics.order_service.application.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="product-service")
public interface ProductClient {
    // product 조회 (param 은 productId)
}
