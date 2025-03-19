package com.sixlab.logistics.delivery_service.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.delivery_service.application.dto.*;
import com.sixlab.logistics.delivery_service.application.service.DeliveryRouteService;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryRouteStatus;
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
public class DeliveryRouteController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    /*
    @GetMapping("/delivery_routes")
    public String getDelivery() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }
    */

    private final DeliveryRouteService deliveryRouteService;

    // 배송 경로 목록 조회
    @GetMapping("/delivery_routes")
    public ApiResponse<Page<DeliveryRouteResponseDto>> getAllDeliveryRoute(
            @ModelAttribute DeliverySearchDto searchDto,
            @PageableDefault(page = 0, size = 10, sort = "deliveryId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<DeliveryRouteResponseDto> deliveryRoute = deliveryRouteService.getAllDeliveryRoute(searchDto, pageable);
        return ApiResponse.success(HttpStatus.OK, deliveryRoute, "SUCCESS");
    }

    // 특정 배송 모든 경로 조회
    @GetMapping("/deliveries/{deliveryId}/routes")
    public ApiResponse<Page<DeliveryRouteResponseDto>> getAllDeliveryRouteByDeliveryId(
            @ModelAttribute DeliverySearchDto searchDto,
            @PageableDefault(page = 0, size = 10, sort = "deliveryId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable UUID deliveryId) {

        Page<DeliveryRouteResponseDto> deliveryRoute = deliveryRouteService.getAllDeliveryRouteByDeliveryId(searchDto, pageable, deliveryId);
        return ApiResponse.success(HttpStatus.OK, deliveryRoute, "SUCCESS");
    }

    // 특정 배송 모든 경로 개별 조회
    @GetMapping("/deliveries/{deliveryId}/routes/{id}")
    public ApiResponse<DeliveryRouteResponseDto> getDeliveryRouteByDeliveryId(
            @PathVariable UUID deliveryId,
            @PathVariable UUID id) {

        DeliveryRouteResponseDto deliveryRoute = deliveryRouteService.getDeliveryRouteByDeliveryId(deliveryId, id);
        return ApiResponse.success(HttpStatus.OK, deliveryRoute, "SUCCESS");
    }

    // 배송 경로 수정
    @PutMapping("/deliveries/{deliveryId}/routes/{id}")
    public ApiResponse<DeliveryRouteResponseDto> updateDeliveryRoute(
            @PathVariable UUID deliveryId,
            @PathVariable UUID id,
            @RequestBody DeliveryRouteRequestDto requestDto) {

        DeliveryRouteResponseDto deliveryRoute = deliveryRouteService.updateDeliveryRoute(deliveryId, id, requestDto);
        return ApiResponse.success(HttpStatus.OK, deliveryRoute, "SUCCESS");
    }

    // 배송 경로 상태 변경
    @PatchMapping("/deliveries/{deliveryId}/routes/{id}/status")
    public ApiResponse<DeliveryRouteStatusResponseDto> updateDeliveryRouteStatus(
            @PathVariable UUID deliveryId,
            @PathVariable UUID id,
            @RequestBody DeliveryRouteRequestDto requestDto) {

        DeliveryRouteStatusResponseDto deliveryRoute = deliveryRouteService.updateDeliveryRouteStatus(deliveryId, id, requestDto.getStatus());
        return ApiResponse.success(HttpStatus.OK, deliveryRoute, "SUCCESS");
    }

    // 배송 경로 삭제
    @DeleteMapping("/deliveries/{deliveryId}/routes/{id}")
    public ApiResponse<DeliveryRouteResponseDto> deleteDeliveryRoute(
            @PathVariable UUID deliveryId,
            @PathVariable UUID id) {
        deliveryRouteService.deleteDeliveryRoute(deliveryId, id);
        return ApiResponse.success(HttpStatus.OK, null, "SUCCESS");
    }

    // 배송 경로 생성(알고리즘으로)

}
