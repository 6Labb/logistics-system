package com.sixlab.logistics.hub_service.hub.application.dto.hub;

import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 허브 정보 조회 응답 Dto
 */
@Getter
@NoArgsConstructor
public class HubResponseDto {

    private String hubName;

    private String hubAddress;

    private double latitude;

    private double longitude;

    private Long hubManagerId;

    @Builder
    private HubResponseDto(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerId) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerId = hubManagerId;
    }

    public static HubResponseDto of(Hub hub) {
        return HubResponseDto.builder()
                .hubName(hub.getHubName())
                .hubAddress(hub.getHubAddress())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .build();
    }

}
