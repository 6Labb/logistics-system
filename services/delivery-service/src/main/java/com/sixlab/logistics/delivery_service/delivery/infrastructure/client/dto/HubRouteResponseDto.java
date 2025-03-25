package com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HubRouteResponseDto {

    private UUID routeId;
    private UUID departureHubId;
    private UUID arrivalHubId;
    private double distance; // 이동거리
    private int duration; // 소요시간

}
