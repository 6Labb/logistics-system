package com.sixlab.logistics.delivery_service.application.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class DeliverySearchDto {

    private UUID deliveryId;
    private String receiveName;
    private String receiveSlackId;
}
