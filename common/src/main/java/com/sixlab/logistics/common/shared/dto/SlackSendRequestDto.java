package com.sixlab.logistics.common.shared.dto;

public record SlackSendRequestDto(
    String channel,
    String text
){}
