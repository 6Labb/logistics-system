package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRouteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRouteResponseDto {

    private UUID id;
    private Integer sequence;
    private Double esimateDistance;
    private Integer esimateTime;
    private Double actualDistance;
    private Integer actualTime;
    private DeliveryRouteStatus status;
    private UUID deliveryId;
    private UUID fromHubId;
    private UUID toHubId;
    private Long deliveryAgentId;

    public DeliveryRouteResponseDto(DeliveryRoute deliveryRoute) {
        this.id = deliveryRoute.getId();
        this.sequence = deliveryRoute.getSequence();
        this.esimateDistance = deliveryRoute.getEstimatedDistance();
        this.esimateTime = deliveryRoute.getEstimatedTime();
        this.status = deliveryRoute.getStatus();
        this.actualDistance = deliveryRoute.getActualDistance();
        this.actualTime = deliveryRoute.getActualTime();
        this.deliveryId = deliveryRoute.getDeliveryId();
        this.fromHubId = deliveryRoute.getFromHubId();
        this.toHubId = deliveryRoute.getToHubId();
        this.deliveryAgentId = deliveryRoute.getDeliveryAgentId();
    }
}
