package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.order_service.application.dto.request.RequestDeliveryRegisterDto;
import com.sixlab.logistics.order_service.application.dto.response.GetCompanyResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.ResponseDeliveryRegisterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name="delivery-service")
public interface DeliveryClient {
    // 배송 서비스의 배송 생성 메서드 호출
    @PostMapping("/deliveries")
    ResponseEntity<ApiResponseDto<ResponseDeliveryRegisterDto>> requestDeliveryRegister(@RequestBody RequestDeliveryRegisterDto dto);

    // 배송 서비스의 삭제 메서드 호출
    @DeleteMapping("/deliveries/{id}")
    ApiResponse<GetProductResponseDto> requestDeliveryDelete(@PathVariable("id") UUID id);
}
