package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderInfoRequestDto {
    private final String productName;
    private final int quantity;
    private final String startLocation;
    private final String stopLocations;
    private final String destination;
    private final String additionalInfo;
    private final String requestMessage;
    private String workingHour;

    @Builder
    public OrderInfoRequestDto(String productName, int quantity, String startLocation,
                               String stopLocations, String destination,
                               String additionalInfo, String requestMessage, String workingHour) {
        this.productName = productName;
        this.quantity = quantity;
        this.startLocation = startLocation;
        this.stopLocations = stopLocations;
        this.destination = destination;
        this.additionalInfo = additionalInfo;
        this.requestMessage = requestMessage;
        this.workingHour = workingHour;
    }


}