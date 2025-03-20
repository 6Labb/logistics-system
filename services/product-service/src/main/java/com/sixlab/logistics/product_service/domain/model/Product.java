package com.sixlab.logistics.product_service.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.product_service.presentaion.dto.ProductRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
                .id(UUID.randomUUID()) // DTO의 ID를 엔티티에 셋팅
                .name(requestDto.getName())
                .quantity(requestDto.getQuantity())
                .hubId(requestDto.getHubId())
                .companyId(requestDto.getCompanyId())
                .build();
    }

    public void updateProduct(ProductRequestDto requestDto) {
        this.name = requestDto.getName();
        this.quantity = requestDto.getQuantity();
        this.hubId = requestDto.getHubId();
        this.companyId = requestDto.getCompanyId();
    }
}
