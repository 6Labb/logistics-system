package com.sixlab.logistics.order_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductStockResponseDto {
    private UUID id; // productId
    private String name; // 상품명
    private Integer quantity; // 상품 재고 수량
    private UUID hubId;
    private UUID companyId; // 공급업체 id
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
