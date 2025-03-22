package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.naver;

import java.util.List;

public record LocationResponseDto(
        String status,
        List<addresses> addresses,
        String errorMessage
) {
    public record addresses(
            String x,
            String y,
            String distance
    ){}
}
