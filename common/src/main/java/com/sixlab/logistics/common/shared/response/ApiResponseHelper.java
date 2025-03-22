package com.sixlab.logistics.common.shared.response;

import java.util.function.Function;

public class ApiResponseHelper {

    public static <T, R> R extractData(ApiResponse<T> response, Function<T, R> extractor) {
        return extractor.apply(response.getBody().getData());
    }
}