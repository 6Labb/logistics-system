package com.sixlab.logistics.order_service.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCompanyResponseDto {
    private UUID id; // 업체 고유 id
    private String name; // 업체명
    private String address; // 업체 주소 (기본값)
    private CompanyType type; // SUPPLIER, RECEIVER 타입이 두 종류
    private UUID hubId; // 허브 id ??
    private LocalDateTime createdAt; // 업체 정보 생성일

    @Getter
    @AllArgsConstructor
    public enum CompanyType {
        SUPPLIER("공급업체"),
        RECEIVER("수령업체");

        // description 반환
        private final String description;
    }
}



