package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
@Getter
@Builder
public class DeliveryClientResponseDto {
    private String hubDeliveryAgentId;
    private String slackId;
    private UUID fromHubId;
    private UUID toHubId;

}
