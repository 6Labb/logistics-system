package com.sixlab.logistics.delivery_service.deliveryAgent.application.dto;

import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class DeliveryAgentSearchDto {

    private DeliveryAgentType type;
    private UUID hubId;
}
