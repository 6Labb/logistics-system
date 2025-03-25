package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.model.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryStatus;
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
    private Long companyDeliveryAgentId;
    private Long hubDeliveryAgentId;
    private UUID fromHubId;
    private UUID toHubId;

    public DeliveryResponseDto(Delivery delivery) {
        this.id = delivery.getId();
        this.status = delivery.getStatus();
        this.deliveryAddress = delivery.getDeliveryAddress();
        this.receiveName = delivery.getReceiveName();
        this.companyDeliveryAgentId = delivery.getCompanyDeliveryAgentId();
        this.hubDeliveryAgentId = delivery.getHubDeliveryAgentId();
        this.fromHubId = delivery.getFromHubId();
        this.toHubId = delivery.getToHubId();
    }

    public DeliveryResponseDto(DeliveryStatus status) {
        this.status = status;
    }

}
