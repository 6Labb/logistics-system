package com.sixlab.logistics.hub_service.hub.application.dto.hub;

import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import lombok.NoArgsConstructor;


public class HubMapper {


    public static Hub toEntity(HubCreateRequestDto dto) {
        return Hub.builder()
                .hubName(dto.getHubName())
                .hubAddress(dto.getHubAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .hubManagerUserId(dto.getHubManagerUserId())
                .build();
    }

    public static HubResponseDto toDto(Hub hub) {
        return HubResponseDto.builder()
                .id(hub.getId())
                .hubName(hub.getHubName())
                .hubAddress(hub.getHubAddress())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .hubManagerUserId(hub.getHubManagerUserId())
                .build();
    }

}
