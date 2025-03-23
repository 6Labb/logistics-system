package com.sixlab.logistics.user_service.auth.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.user_service.auth.application.dto.LoginRequestDto;
import com.sixlab.logistics.user_service.auth.application.dto.LoginResponseDto;
import com.sixlab.logistics.user_service.auth.application.dto.TokenResponseDto;
import com.sixlab.logistics.user_service.auth.application.service.AuthService;
import com.sixlab.logistics.user_service.user.application.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 사용자 ID를 받아 JWT 액세스 토큰을 생성하여 응답합니다.
     *
     *
     * @return JWT 액세스 토큰을 포함한 AuthResponse 객체를 반환합니다.
     */
//    @GetMapping("/auth/signIn")
//    public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, Role role){
//        //String token = authService.createAccessToken(username, role);
//
//        return ResponseEntity.ok()
//                .header("Authorization", "Bearer " + token)
//                .body(new TokenResponseDto(token));
//
//    }

    @PostMapping("/signIn")
    public ApiResponse<?> login(@RequestBody LoginRequestDto request) {
        String token = authService.authenticate(request);
        return ApiResponse.success(token, "로그인 성공");
    }


}
