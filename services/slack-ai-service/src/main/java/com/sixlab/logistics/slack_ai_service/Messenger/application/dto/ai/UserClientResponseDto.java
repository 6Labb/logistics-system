package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserClientResponseDto {
    String slackId;
    String userName;
}
