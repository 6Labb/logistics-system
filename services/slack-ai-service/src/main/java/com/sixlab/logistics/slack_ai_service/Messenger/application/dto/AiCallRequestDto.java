package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

import java.util.List;

public record AiCallRequestDto(
        List<Content> contents
) {
    public record Content(List<Part> parts) {}
    public record Part(String text) {}
}