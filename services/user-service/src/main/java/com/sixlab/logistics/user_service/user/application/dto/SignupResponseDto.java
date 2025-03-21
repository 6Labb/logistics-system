package com.sixlab.logistics.user_service.user.application.dto;

import com.sixlab.logistics.user_service.user.domain.model.User;
import lombok.Builder;
import lombok.Getter;

/**
 *  회원가입 응답 dto
 */
@Builder
@Getter
public class SignupResponseDto {

    private String username;
    private Role role;

}
