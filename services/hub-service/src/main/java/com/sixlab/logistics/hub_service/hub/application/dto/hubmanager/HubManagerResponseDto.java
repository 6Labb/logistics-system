package com.sixlab.logistics.hub_service.hub.application.dto.hubmanager;

import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class HubManagerResponseDto {

    private UUID id;

    private Long userId;

    private UUID hubId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    private HubManagerResponseDto(UUID id, Long userId, UUID hubId) {
        this.id = id;
        this.userId = userId;
        this.hubId = hubId;
    }

    public static HubManagerResponseDto of(HubManager hubManager) {
        return HubManagerResponseDto.builder()
                .id(hubManager.getId())
                .userId(hubManager.getUserId())
                .hubId(hubManager.getHubId())
                .build();
    }

}
