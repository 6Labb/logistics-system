package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SlackMessageInfoDto {
    private String orderId;
    private String customerName;
    private String productName;
    private int quantity;
    private String request;
    private String sender;
    private List<String> transitCenters;
    private String destination;
    private String deliveryManagerName;
    private String deadline;
}