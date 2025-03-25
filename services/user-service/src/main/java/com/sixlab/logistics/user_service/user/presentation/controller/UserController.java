package com.sixlab.logistics.user_service.user.presentation.controller;


import com.sixlab.logistics.common.shared.exception.DuplicateResourceException;
import com.sixlab.logistics.common.shared.exception.InvalidParameterException;
import com.sixlab.logistics.common.shared.exception.OwnershipMismatchException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.user_service.auth.infrastructure.config.UserDetailsImpl;
import com.sixlab.logistics.user_service.user.application.dto.*;
import com.sixlab.logistics.user_service.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     *  회원가입
     */
    @PostMapping("/signUp")
    public ApiResponse<SignupResponseDto> registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        try {
            SignupResponseDto response = userService.registerUser(requestDto);
            return ApiResponse.success(HttpStatus.CREATED, response, "회원가입이 완료되었습니다.");
        } catch (InvalidParameterException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    /**
     *  사용자 상세정보를 조회하여 응답합니다.
     */
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            UserResponseDto response = userService.getUserById(id, userDetails);
            return ApiResponse.success(response, "사용자 정보 조회 성공");

        } catch (OwnershipMismatchException e) {
            return ApiResponse.fail(HttpStatus.FORBIDDEN, e.getMessage());

        } catch (ResourceNotFoundException e) {
            return ApiResponse.fail(HttpStatus.NOT_FOUND, e.getMessage());

        } catch (Exception e) {
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     *  사용자 정보를 수정합니다.
     */
    @PutMapping("/{id}")
    public ApiResponse<UserUpdateResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            UserUpdateResponseDto response = userService.updateUser(id, requestDto, userDetails);
            return ApiResponse.success(response, "사용자 정보가 수정되었습니다.");

        } catch (OwnershipMismatchException e) {
            return ApiResponse.fail(HttpStatus.FORBIDDEN,e.getMessage());

        } catch (InvalidParameterException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage());

        } catch (ResourceNotFoundException e) {
            return ApiResponse.fail(HttpStatus.NOT_FOUND, e.getMessage());

        } catch (Exception e) {
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            userService.deleteUser(id, userDetails);
            return ApiResponse.success(null, "사용자 삭제 완료");

        } catch (OwnershipMismatchException e) {
            return ApiResponse.fail(HttpStatus.FORBIDDEN, e.getMessage());

        } catch (ResourceNotFoundException e) {
            return ApiResponse.fail(HttpStatus.NOT_FOUND, e.getMessage());

        } catch (Exception e) {
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }


    @GetMapping
    public ApiResponse<List<UserResponseDto>> searchUsers(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<UserResponseDto> result = userService.searchUsers(userDetails,keyword, sort, order, page, size);
            return ApiResponse.success(result.getContent(), "사용자 검색 결과");

        } catch (Exception e) {
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
