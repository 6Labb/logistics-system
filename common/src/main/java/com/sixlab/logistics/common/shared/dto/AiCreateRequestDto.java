package com.sixlab.logistics.common.shared.dto;

import java.util.List;

public record AiCreateRequestDto(
        List<Content> contents
) {
    public record Content(List<Part> parts) {}
    public record Part(String text) {}
}