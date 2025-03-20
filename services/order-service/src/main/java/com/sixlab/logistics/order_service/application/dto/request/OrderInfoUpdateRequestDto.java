package com.sixlab.logistics.order_service.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderInfoUpdateRequestDto {
    @Min(value = 1, message = "물품 요청 수량은 1개 이상이어야 합니다.")
    private Integer quantity; // 상품 수량
    private String message; // 요청 사항
}
