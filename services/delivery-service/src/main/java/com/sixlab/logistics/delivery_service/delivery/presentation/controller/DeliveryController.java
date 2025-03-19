package com.sixlab.logistics.delivery_service.delivery.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RefreshScope
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    private final DeliveryService deliveryService;

    /*
    @GetMapping("/deliveries")
    public String getDelivery() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }
    */

    // 배송 리스트 조회
    @GetMapping("/deliveries")
    public ApiResponse<Page<DeliveryResponseDto>> getAllDeliveries(
            @ModelAttribute DeliverySearchDto searchDto,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<DeliveryResponseDto> deliveries = deliveryService.getAllDeliveries(searchDto, pageable);
        return ApiResponse.success(HttpStatus.OK, deliveries, "SUCCESS");
    }

    // 배송 개별 조회
    @GetMapping("/deliveries/{id}")
    public ApiResponse<DeliveryResponseDto> getDelivery(@PathVariable("id") UUID id) {
        DeliveryResponseDto delivery = deliveryService.getDelivery(id);
        return ApiResponse.success(HttpStatus.OK, delivery, "SUCCESS");
    }

    // 배송 수정
    @PutMapping("/deliveries/{id}")
    public ApiResponse<DeliveryResponseDto> updateDelivery(
            @PathVariable UUID id,
            @RequestBody DeliveryRequestDto requestDto) {

        DeliveryResponseDto updateDelivery = deliveryService.updateDelivery(id, requestDto);
        return ApiResponse.success(HttpStatus.OK, updateDelivery, "SUCCESS");
    }

    // 배송 상태 변경
    @PatchMapping("/deliveries/{id}/status")
    public ApiResponse<DeliveryStatusResponseDto> updateDeliveryStatus(
            @PathVariable UUID id,
            @RequestBody DeliveryStatusRequestDto requestDto) {

        DeliveryStatusResponseDto updateDeliveryStatus = deliveryService.updateDeliveryStatus(id, requestDto.getStatus());
        return ApiResponse.success(HttpStatus.OK, updateDeliveryStatus, "SUCCESS");
    }

    // 배송 삭제
    @DeleteMapping("/deliveries/{id}")
    public ApiResponse<Void> deleteDelivery(@PathVariable UUID id) {
        deliveryService.deleteDelivery(id);
        return ApiResponse.success(HttpStatus.OK, null, "SUCCESS");
    }

    // 배송 생성
    @PostMapping("/deliveries")
    public ApiResponse<DeliveryResponseDto> createDelivery(@RequestBody DeliveryRequestDto requestDto) {
        DeliveryResponseDto createDelivery = deliveryService.createDelivery(requestDto);
        return ApiResponse.success(HttpStatus.CREATED, createDelivery, "SUCCESS");
    }

    // 업체 배송 담당자 배정

}
