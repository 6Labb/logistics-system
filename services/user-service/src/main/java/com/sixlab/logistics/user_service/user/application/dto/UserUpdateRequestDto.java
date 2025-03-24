package com.sixlab.logistics.user_service.user.application.dto;

import com.sixlab.logistics.user_service.user.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String username;
    private String password;
    private String slackId;
    private Role role;
    private String adminToken;

    @Builder
    private UserUpdateRequestDto(String username, String password, String slackId, Role role, String adminToken) {
        this.username = username;
        this.password = password;
        this.slackId = slackId;
        this.role = role;
        this.adminToken = adminToken;
    }

    public static UserUpdateRequestDto of(User user) {
        return UserUpdateRequestDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .slackId(user.getSlackId())
                .role(user.getRole())
                .build();
    }


}
