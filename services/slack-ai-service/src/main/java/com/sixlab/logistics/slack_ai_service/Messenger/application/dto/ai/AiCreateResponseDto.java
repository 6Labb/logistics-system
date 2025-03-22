package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai;

import java.util.List;

public record AiCreateResponseDto(
        List<Candidate> candidates
) {
    public record Candidate(
            Content content,
            String finishReason
    ) {}
    public record Content(
            List<Part> parts
    ) {}
    public record Part(String text) {}
}