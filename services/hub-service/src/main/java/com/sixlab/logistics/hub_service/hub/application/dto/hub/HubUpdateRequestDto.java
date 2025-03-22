package com.sixlab.logistics.hub_service.hub.application.dto.hub;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HubUpdateRequestDto {
    private String hubName;
    private String hubAddress;
    private Double latitude;
    private Double longitude;

    @Builder
    public HubUpdateRequestDto(String hubName, String hubAddress, Double latitude, Double longitude) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

