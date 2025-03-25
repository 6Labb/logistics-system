package com.sixlab.logistics.hub_service.hub.application.dto.hub;

import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bouncycastle.crypto.engines.EthereumIESEngine;

import java.util.UUID;

/**
 * 허브 정보 조회 응답 Dto
 */
@Getter
@NoArgsConstructor
public class HubResponseDto {

    private UUID id;

    private String hubName;

    private String hubAddress;

    private double latitude;

    private double longitude;

    private Long hubManagerUserId;

    @Builder
    private HubResponseDto(UUID id, String hubName, String hubAddress, double latitude, double longitude, Long hubManagerUserId) {
        this.id = id;
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerUserId = hubManagerUserId;
    }

    public static HubResponseDto of(Hub hub) {
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
