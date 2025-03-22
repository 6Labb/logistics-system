package com.sixlab.logistics.hub_service.hub.application.dto.hubmanager;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
@Builder
public class HubManagerCreateRequestDto {

    private Long userId;
    private String slackId;
    private UUID hubId;

}
