package com.sixlab.logistics.common.shared.dto;

import java.util.List;

public record DistanceResponseDto(
        Integer code,
        String message,
        String currentDateTime,
        List<Route> route
) {
    public record Route(){}
}
