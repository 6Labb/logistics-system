package com.sixlab.logistics.common.shared.dto;

public record SlackSendResponseDto (
    boolean ok,
    String channel,
    String ts, //전송시간
    String error
){}
