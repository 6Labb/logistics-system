package com.sixlab.logistics.hub_service.hub.application.dto.hubroute;

import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;

import java.util.UUID;

public class HubRouteResponseDto {

    private UUID routeId;
    private String departureHubName;
    private String arrivalHubName;
    private int distance;
    private int duration;

    public static HubRouteResponseDto from(HubRoute route) {
        return new HubRouteResponseDto(
                route.getId(),
                route.getDepartureHub().getName(),
                route.getArrivalHub().getName(),
                route.getDistance(),
                route.getDuration()
        );
    }
}
