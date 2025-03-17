package com.sixlab.logistics.order_service.application.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="user-service")
public interface UserClient {
}
