package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {

    private UUID id;
    private DeliveryStatus status;
    private String deliveryAddress;
    private String receiveName;
    private Long deliveryAgentId;
    private UUID fromHubId;
    private UUID toHubId;

    public DeliveryResponseDto(Delivery delivery) {
        this.id = delivery.getId();
        this.status = delivery.getStatus();
        this.deliveryAddress = delivery.getDeliveryAddress();
        this.receiveName = delivery.getReceiveName();
        this.deliveryAgentId = delivery.getDeliveryAgentId();
        this.fromHubId = delivery.getFromHubId();
        this.toHubId = delivery.getToHubId();
    }

    public DeliveryResponseDto(DeliveryStatus status) {
        this.status = status;
    }
}
