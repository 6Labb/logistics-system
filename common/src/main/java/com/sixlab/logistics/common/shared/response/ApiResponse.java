package com.sixlab.logistics.common.shared.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<ApiResponseDto<T>> {

    private ApiResponse(HttpStatus status, ApiResponseDto<T> body) {
        super(body, status);
    }

    // 성공 응답 (기본 200 OK)
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK, ApiResponseDto.success(data, message));
    }

    // 성공 응답 (커스텀 상태 코드 가능)
    public static <T> ApiResponse<T> success(HttpStatus status, T data, String message) {
        return new ApiResponse<>(status, ApiResponseDto.success(status, data, message));
    }

    // 실패 응답
    public static <T> ApiResponse<T> fail(HttpStatus status, String message) {
        return new ApiResponse<>(status, ApiResponseDto.fail(status, message));
    }
}
