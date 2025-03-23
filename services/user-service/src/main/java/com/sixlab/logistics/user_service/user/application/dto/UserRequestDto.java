package com.sixlab.logistics.user_service.user.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    /**
     * 유저 정보 조회 요청용
     */

    private Long id;
    private String username;
    private String password;
    private String slackId;
    private String email;
    private Role role;

}
