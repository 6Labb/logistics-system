package com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HubManagerResponseDto {

    private Long userId;
    private UUID hubId;

}
