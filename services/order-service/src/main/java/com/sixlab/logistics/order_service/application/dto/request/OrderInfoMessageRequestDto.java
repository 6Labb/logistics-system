package com.sixlab.logistics.order_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoMessageRequestDto {
    private UUID orderId;
    private String productName;
    private Integer quantity;
    private String receiverName;
    private String destination;
    private String requestMessage;
    private UUID deliveryId;
}
