package com.sixlab.logistics.common.shared.dto;

import java.util.List;

public record LocationResponseDto(
        String status,
        List<addresses> addresses,
        String errorMessage
) {
    public record addresses(
            String x,
            String y,
            String distance
    ){}
}
