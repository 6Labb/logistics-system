package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderInfoDto {
    private final String productName;
    private final int quantity;
    private final String sender;
    private final List<String> waypoints;
    private final String destination;
    private final String additionalInfo;
    private String workHours;

    @Builder
    public OrderInfoDto(String productName, int quantity, String sender,
                        List<String> waypoints, String destination,
                        String additionalInfo,String workHours) {

        this.productName = productName;
        this.quantity = quantity;
        this.sender = sender;
        this.waypoints = waypoints;
        this.destination = destination;
        this.additionalInfo = additionalInfo;
        this.workHours = workHours;

    }


}