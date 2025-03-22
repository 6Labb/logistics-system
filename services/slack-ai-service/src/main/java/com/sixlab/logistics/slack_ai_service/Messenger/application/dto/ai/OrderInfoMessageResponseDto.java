package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai;

import lombok.*;

import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoMessageResponseDto {
    private UUID orderId;
    private String productName;
    private Integer quantity;
    private String receiverName;
    private String destination;
    private String requestMessage;
    private UUID deliveryId;
}
