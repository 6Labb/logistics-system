package com.sixlab.logistics.order_service.application.dto.request;

import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.domain.model.Status;
import jakarta.validation.constraints.Email;
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
    @Email
    private String receiverSlackId; // 수령업체의(최종소비자) slackId

    private String receiverCompanyName; // 수령업체의(최종소비자) 이름
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

    public Order toEntity(UUID supplierId, UUID userId) {
        return Order.builder()
                .receiverId(receiverId)
                .productId(productId)
                .address(address)
                .quantity(quantity)
                .message(message)
                .receiverSlackId(receiverSlackId)
                .status(Status.SUCCESS)
                .receiverCompanyName(receiverCompanyName)
                .supplierId(supplierId)
                .quantity(quantity)
                .userId(userId)
                .build();
        // 배달 id 필드의 값은 null
    }
}
