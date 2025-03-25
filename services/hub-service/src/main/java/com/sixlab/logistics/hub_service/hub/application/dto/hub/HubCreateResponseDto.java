package com.sixlab.logistics.hub_service.hub.application.dto.hub;

import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubCreateResponseDto {

    private UUID id;
    private String hubName;
    private String address;
    private LocalDateTime createdAt;

    @Builder
    private HubCreateResponseDto(UUID id, String hubName, String address, LocalDateTime createdAt) {
        this.id = id;
        this.hubName = hubName;
        this.address = address;
        this.createdAt = createdAt;
    }


    public static HubCreateResponseDto of(Hub hub) {
        return HubCreateResponseDto.builder()
                .id(hub.getId())
                .hubName(hub.getHubName())
                .address(hub.getHubAddress())
                .createdAt(hub.getCreatedAt())
                .build();
    }




}
