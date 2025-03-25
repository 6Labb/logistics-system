package com.sixlab.logistics.delivery_service.deliveryAgent.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.service.DeliveryAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequiredArgsConstructor
public class DeliveryAgentController {

    private final DeliveryAgentService deliveryAgentService;

    // 배송 담당자 리스트 조회
    @GetMapping("/delivery-agents")
    public ApiResponse<Page<DeliveryAgentResponseDto>> getAllDeliveryAgents(
            @ModelAttribute DeliveryAgentSearchDto searchDto,
            @PageableDefault(page = 0, size = 10, sort = "type", direction = Sort.Direction.ASC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Page<DeliveryAgentResponseDto> deliveryAgent = deliveryAgentService.getAllDeliveryAgent(searchDto, pageable, userDetails);
        return ApiResponse.success(HttpStatus.OK, deliveryAgent, "SUCCESS");
    }

    // 배송 담당자 개별 조회
    @GetMapping("/delivery-agents/{userId}")
    public ApiResponse<DeliveryAgentResponseDto> getDeliveryAgent(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        DeliveryAgentResponseDto deliveryAgent = deliveryAgentService.getDeliveryAgent(userId, userDetails);

        return ApiResponse.success(HttpStatus.OK, deliveryAgent, "SUCCESS");
    }

    // 배송 담당자 수정
    @PutMapping("/delivery-agents/{userId}")
    public ApiResponse<DeliveryAgentResponseDto> updateDeliveryAgent(
            @PathVariable Long userId,
            @RequestBody DeliveryAgentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        DeliveryAgentResponseDto updateDeliveryAgent = deliveryAgentService.updateDeliveryAgent(userId, requestDto, userDetails);
        return ApiResponse.success(HttpStatus.OK, updateDeliveryAgent, "SUCCESS");
    }

    // 배송 담당자 삭제
    @DeleteMapping("/delivery-agents/{userId}")
    public ApiResponse<Void> deleteDeliveryAgent(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        deliveryAgentService.deleteDeliveryAgent(userId, userDetails);
        return ApiResponse.success(HttpStatus.OK, null, "SUCCESS");
    }

    // 배송 담당자 생성
    @PostMapping("/delivery-agents")
    public ApiResponse<DeliveryAgentResponseDto> createDeliveryAgent(
            @RequestBody DeliveryAgentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        DeliveryAgentResponseDto createDeliveryAgent = deliveryAgentService.createDeliveryAgent(requestDto, userDetails);
        return ApiResponse.success(HttpStatus.CREATED, createDeliveryAgent, "SUCCESS");
    }


}
