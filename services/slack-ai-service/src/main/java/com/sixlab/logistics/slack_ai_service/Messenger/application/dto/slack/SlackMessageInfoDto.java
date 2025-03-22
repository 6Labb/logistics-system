package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SlackMessageInfoDto {
    private UUID orderId;
    private String customerName;
    private String productName;
    private int quantity;
    private String request;
    private String sender;
    private String transitCenters;
    private String destination;
    private String deliveryManagerName;
    private String deadline;
}