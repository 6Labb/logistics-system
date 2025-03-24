package com.sixlab.logistics.hub_service.hub.application.dto.hubmanager;

import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubManagerCreateResponseDto {
    private UUID id;
    private Long userId;
    private String slackId;
    private UUID hubId;

    @Builder
    private HubManagerCreateResponseDto(UUID id, Long userId, String slackId, UUID hubId) {
        this.id = id;
        this.userId = userId;
        this.slackId = slackId;
        this.hubId = hubId;
    }

    public static HubManagerCreateResponseDto of(HubManager hubManager) {
        return HubManagerCreateResponseDto.builder()
                .id(hubManager.getId())
                .userId(hubManager.getUserId())
                .slackId(hubManager.getSlackId())
                .hubId(hubManager.getHubId())
                .build();
    }
}
