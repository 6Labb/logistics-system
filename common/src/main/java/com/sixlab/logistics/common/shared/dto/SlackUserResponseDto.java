package com.sixlab.logistics.common.shared.dto;

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



