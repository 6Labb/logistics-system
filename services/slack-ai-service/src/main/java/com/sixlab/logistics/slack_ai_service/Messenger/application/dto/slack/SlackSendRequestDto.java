package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack;

public record SlackSendRequestDto(
    String channel,
    String text
){}
