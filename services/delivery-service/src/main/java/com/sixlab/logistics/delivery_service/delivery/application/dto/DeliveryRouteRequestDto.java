package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRouteStatus;
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
    private DeliveryRouteStatus status;
    private Long companyDeliveryAgentId;
    private Long hubDeliveryAgentId;
    private UUID hubRouteId;
}
