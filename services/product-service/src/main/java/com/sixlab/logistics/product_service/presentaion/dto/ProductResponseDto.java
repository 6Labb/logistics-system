package com.sixlab.logistics.product_service.presentaion.dto;

import com.sixlab.logistics.product_service.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private UUID id;
    private String name;
    private Integer quantity;
    private UUID hubId;
    private UUID companyId;
    private LocalDateTime createdAt;

    public ProductResponseDto(UUID id, UUID companyId, UUID hubId, Integer quantity, String name, LocalDateTime createdAt) {
        this.id = id;
        this.companyId = companyId;
        this.hubId = hubId;
        this.quantity = quantity;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getCompanyId(),
                product.getHubId(),
                product.getQuantity(),
                product.getName(),
                product.getCreatedAt()
        );
    }
}
