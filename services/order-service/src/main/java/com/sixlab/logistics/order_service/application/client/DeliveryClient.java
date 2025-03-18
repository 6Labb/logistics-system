package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.order_service.application.dto.request.RequestDeliveryRegisterDto;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="delivery-service")
public interface DeliveryClient {
    // 배송 생성 메서드 호출
    @GetMapping("/deliveries")
    ApiResponse<GetProductResponseDto> requestDeliveryRegister(@RequestBody RequestDeliveryRegisterDto dto);
}
