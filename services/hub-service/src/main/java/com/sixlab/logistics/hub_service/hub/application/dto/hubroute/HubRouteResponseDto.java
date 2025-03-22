package com.sixlab.logistics.hub_service.hub.application.dto.hubroute;

import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubRouteResponseDto {

    private UUID routeId;
    private String departureHubName;
    private String arrivalHubName;
    private int distance;
    private int duration;

    private HubRouteResponseDto(UUID id, String hubName, String hubName1, int distance, int duration) {
        this.routeId = id;
        this.departureHubName = hubName;
        this.arrivalHubName = hubName1;
        this.distance = distance;
        this.duration = duration;
    }

    public static HubRouteResponseDto from(HubRoute route) {
        return new HubRouteResponseDto(
                route.getId(),
                route.getDepartureHub().getHubName(),
                route.getArrivalHub().getHubName(),
                route.getDistance(),
                route.getDuration()
        );
    }
}
