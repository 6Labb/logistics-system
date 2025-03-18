package com.sixlab.logistics.delivery_service.application.dto;

import com.sixlab.logistics.delivery_service.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
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
    private String receiveSlackId;
    private UUID companyDeliveryAgentId;

    public DeliveryResponseDto(Delivery delivery) {
        this.id = delivery.getId();
        this.status = delivery.getStatus();
        this.deliveryAddress = delivery.getDeliveryAddress();
        this.receiveName = delivery.getReceiveName();
        this.receiveSlackId = delivery.getReceiveSlackId();
        this.companyDeliveryAgentId = delivery.getCompanyDeliveryAgentId();
    }
}
