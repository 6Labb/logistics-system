package com.sixlab.logistics.common.infrastructure.config.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponseDto<T> {
    private final int status;
    private final String message;
    private final T data;

    @Builder
    private ApiResponseDto(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 성공 응답 (메시지를 동적으로 설정)
    public static <T> ApiResponseDto<T> success(T data, String message) {
        return ApiResponseDto.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponseDto<T> success(HttpStatus status, T data, String message) {
        return ApiResponseDto.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
    }

    // 실패 응답 (메시지를 동적으로 설정)
    public static <T> ApiResponseDto<T> fail(HttpStatus status, String message) {
        return ApiResponseDto.<T>builder()
                .status(status.value())
                .message(message)
                .data(null)
                .build();
    }
}

