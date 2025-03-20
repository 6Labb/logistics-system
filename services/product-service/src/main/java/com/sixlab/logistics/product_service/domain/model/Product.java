package com.sixlab.logistics.product_service.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.product_service.presentaion.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_product", schema = "products")
public class Product extends BasicEntity {
    @Id
    private UUID id;
    private String name;
    private Integer quantity;
    private UUID hubId;
    private UUID companyId;

    // DTO에서 Entity로 변환하는 메서드
    public static Product toEntity(ProductRequestDto requestDto) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(requestDto.getName())
                .quantity(requestDto.getQuantity())
                .hubId(requestDto.getHubId())
                .companyId(requestDto.getCompanyId())
                .build();
    }

    public void updateProduct(ProductRequestDto requestDto) {
        if (requestDto.getName() != null) this.name = requestDto.getName();
        if (requestDto.getQuantity() != null) this.quantity = requestDto.getQuantity();
        if (requestDto.getHubId() != null) this.hubId = requestDto.getHubId();
        if (requestDto.getCompanyId() != null) this.companyId = requestDto.getCompanyId();
    }

    public void decreaseStock(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.quantity -= quantity;
    }

    public void increaseStock(int quantity) {
        this.quantity += quantity;
    }
}
