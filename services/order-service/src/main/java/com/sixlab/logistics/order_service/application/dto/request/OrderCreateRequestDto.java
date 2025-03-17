package com.sixlab.logistics.order_service.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class OrderCreateRequestDto {
    @NotNull
    private UUID receiverId; // 수령업체 UUID
    @NotNull
    private UUID productId; // 상품 UUID

    @NotBlank
    private String address; // 배송지: 물건을 수령할 주소
    @NotNull
    private Integer quantity; // 상품 수량
    @NotBlank
    private String message; // 요청사항
}
