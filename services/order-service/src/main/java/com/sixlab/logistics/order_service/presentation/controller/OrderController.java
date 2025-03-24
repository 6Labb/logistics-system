package com.sixlab.logistics.order_service.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.common.shared.security.UserInfo;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sixlab.logistics.order_service.application.dto.request.OrderInfoUpdateRequestDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderCreateResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderDeleteResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderFindOneResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderInfoUpdateResponseDto;
import com.sixlab.logistics.order_service.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    // 모든 권한 접근 허용
    // @PreAuthorize()
    public ApiResponse<OrderCreateResponseDto> createOrder(
            @RequestBody @Valid OrderCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        // log.info("createOrder: {}", requestDto);
        // userId 만 서비스에 전달, createdBy 세팅할 예정
        OrderCreateResponseDto order = orderService.createOrder(requestDto, userDetails.getUserId());
        return ApiResponse.success(order, "주문이 성공적으로 등록되었습니다.");
    }

    // 실험용: 유저 객체
    // public UserInfo user = new UserInfo(1L, UserInfo.Role.HUB_MANAGER);


    @GetMapping
    @Operation(summary = "주문 전체 조회")
    public ApiResponse<?> findAllOrders(UserInfo user) {
        List<OrderFindOneResponseDto> dtoList = orderService.getOrderListByRole(user);
        if(dtoList.isEmpty()) return ApiResponse.success(null, "주문내역이 존재하지 않습니다.");
        return ApiResponse.success(dtoList, dtoList.size()+" 건의 주문내역이 조회되었습니다.");
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 단건 조회")
    // DELIVERY_AGENT(본인 주문), TRADE_PARTNER(본인 주문), HUB_MANAGER(담당 허브), MASTER
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'DELIVERY_AGENT', 'TRADE_PARTNER')")
    public ApiResponse<OrderFindOneResponseDto> findOneOrder(@PathVariable UUID orderId,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("/orders/{}: findOneOrder 메서드 호출", orderId);
        log.info("사용자의 userId: {}, authority: {}", userDetails.getUserId(), userDetails.getAuthorities());
        OrderFindOneResponseDto findOrder = orderService.findOneOrder(orderId, userDetails);// (orderId, userPrincipal)
        return ApiResponse.success(findOrder, "주문이 성공적으로 조회되었습니다.");
    }

    // 마스터와 담당 허브매니저만 호출할 수 있는 수정 메서드
    // *** 수정할 수 있는 사항은 상품수량과 요청사항으로 한정한다.
    @Operation(summary = "주문 수정")
    @PatchMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    public ApiResponse<OrderInfoUpdateResponseDto> orderInfoUpdate(@PathVariable UUID orderId,
                                          @RequestBody OrderInfoUpdateRequestDto dto,
                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        OrderInfoUpdateResponseDto updateOrderInfo = orderService.orderInfoUpdate(orderId, dto, userDetails);
        return ApiResponse.success(updateOrderInfo, "주문정보가 성공적으로 수정되었습니다.");
    }

    // 접근 권한: MASTER, HUB_MANAGER (담당)
    @Operation(summary = "주문 삭제")
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    public ApiResponse<OrderDeleteResponseDto> deleteOrder(@PathVariable UUID orderId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        OrderDeleteResponseDto dto = orderService.deleteOrder(orderId, userDetails);
        return ApiResponse.success(dto, "주문이 성공적으로 삭제되었습니다.(소프트 삭제)");
    }








}
