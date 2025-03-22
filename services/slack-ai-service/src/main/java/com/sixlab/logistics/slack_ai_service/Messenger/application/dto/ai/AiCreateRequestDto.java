package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai;

import java.util.List;

public record AiCreateRequestDto(
        List<Content> contents
) {
    public record Content(List<Part> parts) {}
    public record Part(String text) {}
}