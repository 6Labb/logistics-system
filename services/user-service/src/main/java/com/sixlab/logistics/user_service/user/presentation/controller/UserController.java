package com.sixlab.logistics.user_service.user.presentation.controller;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.user_service.user.application.dto.SignupRequestDto;
import com.sixlab.logistics.user_service.user.application.dto.SignupResponseDto;
import com.sixlab.logistics.user_service.user.application.dto.UserResponseDto;
import com.sixlab.logistics.user_service.user.application.dto.UserUpdateRequestDto;
import com.sixlab.logistics.user_service.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("/test")
    public String getUser() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }

    private final UserService userService;


    /**
     *  회원가입
     */
    @PostMapping("/signUp")
    public ApiResponse<SignupResponseDto> registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        SignupResponseDto response = userService.registerUser(requestDto);
        return ApiResponse.success(HttpStatus.CREATED, response, "회원가입이 완료되었습니다.");
    }

    /**
     *  사용자 상세정보를 조회하여 응답합니다.
     */
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable("id") Long id,
                                                @RequestHeader("X-User-Id") String userIdHeader,
                                                @RequestHeader("X-User-Role") String userRoleHeader) {

        System.out.println("🚀 UserController - 요청받음: userId=" + id + ", X-User-Id=" + userIdHeader + ", X-User-Role=" + userRoleHeader);
        UserResponseDto response = userService.getUserById(id);

        return ApiResponse.success(response, "사용자 정보 조회 성공");
    }

    @GetMapping("/v1/{id}")
    public ApiResponse<UserResponseDto> getUser2(@PathVariable("id") Long id) {

        UserResponseDto response = userService.getUserById(id);

        return ApiResponse.success(response, "사용자 정보 조회 성공");
    }









}
