package com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

    //private UUID departureHubId;
    //private UUID arrivalHubId;
    private UUID supplierId;
    private UUID receiverId;

}
