package com.sixlab.logistics.order_service.application.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="delivery-service")
public interface DeliveryClient {
    // 배송 생성 메서드 호출
}
