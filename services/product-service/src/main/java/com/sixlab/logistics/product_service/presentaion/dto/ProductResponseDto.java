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
    private String quantity;
    private UUID hubId;
    private UUID companyId;
    private LocalDateTime createdAt;
    private String createdBy;

    public ProductResponseDto(UUID id, UUID companyId, UUID hubId, String quantity, String name) {
        this.id = id;
        this.companyId = companyId;
        this.hubId = hubId;
        this.quantity = quantity;
        this.name = name;
    }

    public ProductResponseDto(Product product){
        this.id = product.getId();
        this.companyId = product.getCompanyId();
        this.hubId = product.getHubId();
        this.quantity = product.getQuantity().toString();
        this.name = product.getName();
        this.createdAt = product.getCreatedAt();
        this.createdBy = product.getCreatedBy().toString();
    }
}
