package com.sixlab.logistics.hub_service.hub.application.dto.hubroute;

import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubRouteResponseDto {

    private UUID routeId;
    private UUID departureHubId;
    private UUID arrivalHubId;
    private int distance;
    private int duration;

    private HubRouteResponseDto(UUID id, UUID departureHubId, UUID arrivalHubId, int distance, int duration) {
        this.routeId = id;
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.distance = distance;
        this.duration = duration;
    }

    public static HubRouteResponseDto from(HubRoute route) {
        return new HubRouteResponseDto(
                route.getId(),
                route.getDepartureHub().getId(),
                route.getArrivalHub().getId(),
                route.getDistance(),
                route.getDuration()
        );
    }
}
