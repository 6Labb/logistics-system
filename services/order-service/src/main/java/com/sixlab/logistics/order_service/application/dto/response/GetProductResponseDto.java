package com.sixlab.logistics.order_service.application.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class GetProductResponseDto {
    private UUID id; // 상품 ID
    private UUID companyId; // 공급업체 ID
    private UUID hubId; // 허브 ID
    private String productName; // 1. 필드명을 name 이라고 해야할지 은선님께 물어봐야함.
    private Integer quantity; // 2. 허브 ID 가 관리하는 상품의 재고 수량이라고 합의함.
    private LocalDateTime createdAt; // 상품 ID 생성일
    // 3. 상품을 생성한 userId 가 값으로 담긴다면 Integer 타입으로 해야할듯,
    // user 테이블의 id 가 bigint 타입임. bigint 라 Long 타입으로 해야하나??
    private Integer createdBy;
}
