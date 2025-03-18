package com.sixlab.logistics.order_service.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderCreateResponseDto;
import com.sixlab.logistics.order_service.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/orders")
@Slf4j
@ControllerAdvice
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


//    @GetMapping
//    public String getOrder() {
//        log.info("http://localhost:19096/orders: GET");
//        return "info!!! From port : " + serverPort + "and message : " + message;
//    }



    @Operation(summary = "주문 등록")
    @PostMapping
    public ApiResponse<OrderCreateResponseDto> createOrder(
            @RequestBody @Valid OrderCreateRequestDto requestDto) throws Exception {
        log.info("createOrder: {}", requestDto);
        OrderCreateResponseDto order = orderService.createOrder(requestDto);
        return ApiResponse.success(order, "주문이 성공적으로 등록되었습니다.");
    }

//    @GetMapping("/{orderId}")
//    @Operation(summary = "주문 단건 조회")
//    public ApiResponse<?> findOneOrder(){
//
//    }







}
