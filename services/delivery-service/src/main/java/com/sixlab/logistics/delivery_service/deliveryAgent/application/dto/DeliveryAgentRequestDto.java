package com.sixlab.logistics.delivery_service.deliveryAgent.application.dto;

import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentRequestDto {

    private Long userId;
    private DeliveryAgentType type;
    private Integer deliverySequence;
    private UUID hubId;
    private String slackId;

}
