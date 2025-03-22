package com.sixlab.logistics.order_service.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.order_service.application.dto.UserInfo;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sixlab.logistics.order_service.application.dto.request.OrderInfoUpdateRequestDto;
import com.sixlab.logistics.order_service.application.dto.response.*;
import com.sixlab.logistics.order_service.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RefreshScope
@RestController
@RequestMapping("/orders")
@Slf4j(topic = "order-service controller")
@ControllerAdvice
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @Operation(summary = "주문 등록")
    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<OrderCreateResponseDto> createOrder(
            @RequestBody @Valid OrderCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        log.info("userDetails.getUserId() - {}, userDetails.getUserInfo() - {}", userDetails.getUserId(), userDetails.getUserInfo().toString());
        log.info("createOrder: {}", requestDto);
        OrderCreateResponseDto order = orderService.createOrder(requestDto);
        return ApiResponse.success(order, "주문이 성공적으로 등록되었습니다.");
    }

    // 실험용: 유저 객체
    public UserInfo user = new UserInfo(1L, UserInfo.Role.HUB_MANAGER);


    @GetMapping
    @Operation(summary = "주문 전체 조회")
    public ApiResponse<?> findAllOrders(UserInfo user) {
        List<OrderFindOneResponseDto> dtoList = orderService.getOrderListByRole(user);
        if(dtoList.isEmpty()) return ApiResponse.success(null, "주문내역이 존재하지 않습니다.");
        return ApiResponse.success(dtoList, dtoList.size()+" 건의 주문내역이 조회되었습니다.");
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 단건 조회")
    public ApiResponse<OrderFindOneResponseDto> findOneOrder(@PathVariable UUID orderId
                                       /*@AuthenticationPrincipal UserPrincipal userPrincipal*/){
        // 로그 찍히는거 확인 - /orders/24938764-7944-4f95-9774-5c8d6335b256: findOneOrder 메서드 호출
        log.info("/orders/{}: findOneOrder 메서드 호출", orderId);
        OrderFindOneResponseDto findOrder = orderService.findOneOrder(orderId);// (orderId, userPrincipal)
        return ApiResponse.success(findOrder, "주문이 성공적으로 조회되었습니다.");
    }

    // 마스터와 허브매니저만 호출할 수 있는 수정 메서드
    // *** 수정할 수 있는 사항은 상품수량과 요청사항으로 한정한다.
    @Operation(summary = "주문 수정")
    @PatchMapping("/{orderId}")
    public ApiResponse<OrderInfoUpdateResponseDto> orderInfoUpdate(@PathVariable UUID orderId,
                                          @RequestBody OrderInfoUpdateRequestDto dto) {

        OrderInfoUpdateResponseDto updateOrderInfo = orderService.orderInfoUpdate(orderId, dto);
        return ApiResponse.success(updateOrderInfo, "주문정보가 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "주문 삭제")
    @DeleteMapping("/{orderId}")
    public ApiResponse<OrderDeleteResponseDto> deleteOrder(@PathVariable UUID orderId) {
        OrderDeleteResponseDto dto = orderService.deleteOrder(orderId);
        return ApiResponse.success(dto, "주문이 성공적으로 삭제되었습니다.(소프트 삭제)");
    }








}
