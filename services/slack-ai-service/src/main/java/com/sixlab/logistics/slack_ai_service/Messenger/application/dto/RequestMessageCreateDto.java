package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

import lombok.Getter;

@Getter
public class RequestMessageCreateDto {
    private String slackId;
    private String message;
    private String messageStatus;
}
