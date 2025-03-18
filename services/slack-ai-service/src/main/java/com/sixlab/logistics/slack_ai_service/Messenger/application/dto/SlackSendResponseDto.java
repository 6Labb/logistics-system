package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

public record SlackSendResponseDto (
    boolean ok,
    String channel,
    String ts, //전송시간
    String error
){}
