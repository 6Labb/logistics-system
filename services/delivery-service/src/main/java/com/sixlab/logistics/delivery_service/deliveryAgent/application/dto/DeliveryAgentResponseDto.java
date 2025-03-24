package com.sixlab.logistics.delivery_service.deliveryAgent.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentResponseDto {

    private Long userId;
    private DeliveryAgentType type;
    private Integer deliverySequence;
    private UUID hubId;
    private String slackId;

    public DeliveryAgentResponseDto(DeliveryAgent deliveryAgent) {
        this.userId = deliveryAgent.getUserId();
        this.type = deliveryAgent.getType();
        this.deliverySequence = deliveryAgent.getDeliverySequence();
        this.hubId = deliveryAgent.getHubId();
        this.slackId = deliveryAgent.getSlackId();
    }

    public DeliveryAgentResponseDto(String slackId) {
        this.slackId = slackId;
    }
}
