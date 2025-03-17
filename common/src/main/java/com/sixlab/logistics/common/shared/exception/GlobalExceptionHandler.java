package com.sixlab.logistics.common.shared.exception;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ❌ 삭제된 데이터 접근
    @ExceptionHandler(DeletedDataAccessException.class)
    public ApiResponse<String> handleDeletedDataAccess(DeletedDataAccessException ex) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    // ❌ 존재하지 않는 데이터 접근
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    // ❌ 이미 존재하는 데이터 생성 (409)
    @ExceptionHandler(DuplicateResourceException.class)
    public ApiResponse<String> handleDuplicateResource(DuplicateResourceException ex) {
        return ApiResponse.fail(HttpStatus.CONFLICT,ex.getMessage());
    }

    // ❌ 권한 없음 (401)
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ApiResponse<String> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        return ApiResponse.fail(HttpStatus.UNAUTHORIZED,ex.getMessage());
    }

    // ❌ 소유자가 아닌 사용자의 접근 (403)
    @ExceptionHandler(OwnershipMismatchException.class)
    public ApiResponse<String> handleOwnershipMismatch(OwnershipMismatchException ex) {
        return ApiResponse.fail(HttpStatus.FORBIDDEN,ex.getMessage());
    }

    // ❌ 잘못된 요청 파라미터 (400)
    @ExceptionHandler(InvalidParameterException.class)
    public ApiResponse<String> handleInvalidParameter(InvalidParameterException ex) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    // ❌ 수행할 수 없는 작업 요청 (400 또는 403)
    @ExceptionHandler(OperationNotAllowedException.class)
    public ApiResponse<String> handleOperationNotAllowed(OperationNotAllowedException ex) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    // ❌ 서버 내부 오류 (500)
    @ExceptionHandler(InternalServerException.class)
    public ApiResponse<String> handleInternalServerError(InternalServerException ex) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
    }
}

