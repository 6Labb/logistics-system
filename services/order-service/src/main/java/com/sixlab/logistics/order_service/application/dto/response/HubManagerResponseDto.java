package com.sixlab.logistics.order_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubManagerResponseDto {
    private UUID id;
    private Long userId;
    private UUID hubId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
