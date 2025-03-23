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

    private UUID departureHubId;
    private UUID arrivalHubId;


    private HubRouteRequestDto(UUID departureHubId, UUID arrivalHubId) {

        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;

    }

    public static HubRouteRequestDto from(HubRoute route) {
        return new HubRouteRequestDto(
                route.getDepartureHub().getId(),
                route.getArrivalHub().getId()
        );
    }
}
