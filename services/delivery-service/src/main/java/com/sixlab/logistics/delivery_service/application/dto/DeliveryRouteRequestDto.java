package com.sixlab.logistics.delivery_service.application.dto;

import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRouteRequestDto {

    private Integer sequence;
    private Double esimateDistance;
    private Integer esimateTime;
    private Double actualDistance;
    private Integer actualTime;
    private DeliveryStatus status;
    private UUID deliveryId;
    private UUID depatureId;
    private UUID arrivalHubId;
    private UUID hubDeliveryAgentId;
}
