package com.sixlab.logistics.user_service.auth.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.user_service.auth.application.dto.LoginRequestDto;
import com.sixlab.logistics.user_service.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    /**
     * 사용자 ID(username)와 password 를 받아 JWT 액세스 토큰을 생성하여 응답합니다.
     *
     * @return JWT 액세스 토큰을 포함한 AuthResponse 객체를 반환합니다.
     */
    @PostMapping("/signIn")
    public ApiResponse<?> login(@RequestBody LoginRequestDto request) {
        String token = authService.authenticate(request);

        if (token == null) {
            return ApiResponse.fail(HttpStatus.UNAUTHORIZED,"아이디와 비밀번호를 확인해주세요");
        }

        return ApiResponse.success(token, "로그인 성공");
    }


}
