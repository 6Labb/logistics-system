package com.sixlab.logistics.delivery_service.delivery.application.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class DeliverySearchDto {

    private Long deliveryAgentId;
    private String receiveName;
}
