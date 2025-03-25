package com.sixlab.logistics.common.shared.response;

import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class ApiResponseHelper {

    public static <T, R> R extractData(ResponseEntity<ApiResponseDto<T>> responseEntity, Function<T, R> extractor) {
        ApiResponseDto<T> response = responseEntity.getBody();
        if (response == null || response.getData() == null) {
            throw new IllegalStateException("응답 바디가 비어있습니다.");
        }
        return extractor.apply(response.getData());
    }
}