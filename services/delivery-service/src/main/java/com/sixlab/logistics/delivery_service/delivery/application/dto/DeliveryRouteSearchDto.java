package com.sixlab.logistics.delivery_service.delivery.application.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class DeliveryRouteSearchDto {

    private UUID deliveryId;
    private Long companyDeliveryAgentId;
    private Long hubDeliveryAgentId;
}

