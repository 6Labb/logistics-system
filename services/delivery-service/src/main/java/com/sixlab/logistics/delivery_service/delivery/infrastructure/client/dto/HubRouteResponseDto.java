package com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HubRouteResponseDto {

    private UUID id;
    private UUID fromHubId;
    private UUID toHubId;
    private Integer totalDuration; // 소요시간
    private Double routeDistance; // 이동거리

}
