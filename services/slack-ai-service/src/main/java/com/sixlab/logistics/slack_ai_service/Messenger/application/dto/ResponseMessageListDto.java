package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ResponseMessageListDto {
    private UUID id;
    private String slackId;
    private String message;
    private String messageStatus;
}
