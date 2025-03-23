package com.sixlab.logistics.hub_service.hub.application.dto.hubroute;

import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubRouteRequestDto {

    private UUID hubRouteId;
    private UUID departureHubId;
    private UUID arrivalHubId;
    private int distance;
    private int duration;

    private HubRouteRequestDto(UUID id, UUID departureHubId, UUID arrivalHubId, int distance, int duration) {
        this.hubRouteId = id;
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.distance = distance;
        this.duration = duration;
    }

    public static HubRouteRequestDto from(HubRoute route) {
        return new HubRouteRequestDto(
                route.getId(),
                route.getDepartureHub().getId(),
                route.getArrivalHub().getId(),
                route.getDistance(),
                route.getDuration()
        );
    }
}
