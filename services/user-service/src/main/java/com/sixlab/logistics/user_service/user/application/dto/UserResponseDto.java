package com.sixlab.logistics.user_service.user.application.dto;

import com.sixlab.logistics.user_service.user.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 조회 응답 Dto
 */
@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private String slackId;
    private Role role;

    @Builder
    private UserResponseDto(Long id, String username, String slackId, Role role) {
        this.id = id;
        this.username = username;
        this.slackId = slackId;
        this.role = role;
    }

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .slackId(user.getSlackId())
                .role(user.getRole())
                .build();
    }
}
