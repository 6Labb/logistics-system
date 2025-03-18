package com.sixlab.logistics.slack_ai_service.Messenger.application.dto;

public record SlackUserResponseDto(
        boolean ok,
        String error,
        SlackUser user
) {
    public record SlackUser(
            String id,
            SlackProfile profile
    ) {
        public record SlackProfile(
                String email
        ) {}
    }
}



