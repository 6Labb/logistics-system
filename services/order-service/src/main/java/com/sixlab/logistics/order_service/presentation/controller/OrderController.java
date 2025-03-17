package com.sixlab.logistics.order_service.presentation.controller;

import com.sixlab.logistics.common.exception.ApiResponse;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderCreateResponseDto;
import com.sixlab.logistics.order_service.application.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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

    /*
        @GetMapping
        public String getOrder() {
            log.info("http://localhost:19096/orders: GET");
            return "info!!! From port : " + serverPort + "and message : " + message;
        }
    */

    @PostMapping
    public ApiResponse<OrderCreateResponseDto> createOrder(
            @RequestBody @Valid OrderCreateRequestDto requestDto) throws Exception {
         /*
            UUID receiverId, productId,
            String address, Integer quantity, String message
        */
        log.info("createOrder: {}", requestDto);
        orderService.createOrder(requestDto);
        return null;
    }







}
