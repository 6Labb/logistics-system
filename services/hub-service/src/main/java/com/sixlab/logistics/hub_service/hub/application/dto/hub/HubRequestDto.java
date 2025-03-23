package com.sixlab.logistics.hub_service.hub.application.dto.hub;


import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HubRequestDto {

    private String hubName;
    private String hubAddress;
    private double latitude;
    private double longitude;
    private Long hubManagerUserId;

    @Builder
    private HubRequestDto(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerUserId) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerUserId = hubManagerUserId;
    }


    public static Hub from(HubCreateRequestDto dto) {
        return Hub.builder()
                .hubName(dto.getHubName())
                .hubAddress(dto.getHubAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .hubManagerUserId(dto.getHubManagerUserId())
                .build();
    }
}
