package com.sixlab.logistics.hub_service.hub.application.dto.hubroute;


import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubRouteUpdateRequestDto {

    private UUID departureHubId;
    private UUID arrivalHubId;
    private double distance;
    private int duration;

    private HubRouteUpdateRequestDto(UUID departureHubId, UUID arrivalHubId, double distance, int duration) {
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.distance = distance;
        this.duration = duration;
    }

    public static HubRouteUpdateRequestDto from(HubRoute route) {
        return new HubRouteUpdateRequestDto(
                route.getDepartureHub().getId(),
                route.getArrivalHub().getId(),
                route.getDistance(),
                route.getDuration()
        );
    }
}
