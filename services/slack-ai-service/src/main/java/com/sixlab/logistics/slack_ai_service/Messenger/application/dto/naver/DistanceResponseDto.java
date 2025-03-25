package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.naver;

import java.util.List;

public record DistanceResponseDto(
        Integer code,
        String message,
        String currentDateTime,
        List<Route> route
) {
    public record Route(){}
}
