package com.sixlab.logistics.delivery_service.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeliveryAgentDto {

    private UUID deliveryAgentId;
    private UUID hubId;
    private Long userId;
    private Integer deliverySequence;
}
