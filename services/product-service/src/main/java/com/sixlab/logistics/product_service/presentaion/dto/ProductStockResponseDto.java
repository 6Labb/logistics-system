package com.sixlab.logistics.product_service.presentaion.dto;

import com.sixlab.logistics.product_service.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockResponseDto {
    private UUID id;
    private String name;
    private Integer quantity;
    private UUID hubId;
    private UUID companyId;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    // Product 엔티티에서 DTO로 변환하는 메서드
    public static ProductStockResponseDto fromEntity(Product product) {
        return new ProductStockResponseDto(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getHubId(),
                product.getCompanyId(),
                product.getUpdatedAt(),
                product.getUpdatedBy()
        );
    }
}
