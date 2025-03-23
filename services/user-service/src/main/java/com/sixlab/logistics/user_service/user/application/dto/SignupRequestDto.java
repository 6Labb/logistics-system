package com.sixlab.logistics.user_service.user.application.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

/**
 *  회원가입 요청 dto
 */
@Getter
@Builder
public class SignupRequestDto {

    // username은 최소 4자 이상, 10자 이하, 알파벳 소문자(a~z), 숫자(0~9)로 구성
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min = 4, max = 10, message = "아이디는 4~10자로 입력해야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;

    // password는 최소 8자 이상, 15자 이하, 알파벳 대소문자(A~z), 숫자(0~9), 특수문자로 구성
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8~15자로 입력해야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()_+=~]+$")
    private String password;

    @NotBlank(message = "슬랙 ID는 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String slackId;

    @NotNull
    private Role role;

    private String adminToken;


}
