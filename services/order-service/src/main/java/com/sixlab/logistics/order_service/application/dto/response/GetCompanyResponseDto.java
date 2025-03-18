package com.sixlab.logistics.order_service.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCompanyResponseDto {
    private UUID id; // 업체 고유 id
    private String name; // 업체명
    private Type type; // SUPPLIER, RECEIVER 타입이 두 종류
    private UUID hubId; // 허브 id ??
    private LocalDateTime createdAt; // 업체 정보 생성일

    public enum Type {
        SUPPLIER, RECEIVER
    }

}



