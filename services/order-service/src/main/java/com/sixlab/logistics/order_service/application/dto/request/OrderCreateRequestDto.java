package com.sixlab.logistics.order_service.application.dto.request;

import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.domain.model.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
    // @Email
    // private String receiverSlackId; // 수령업체의(최종소비자) slackId

    // 수령인: 수령업체에서 받을 사람의 이름, 수령인은 달라질 수 있기에 클라이언트로부터 직접 전달받는다.
    @NotBlank(message = "수령인 작성은 필수 입니다.")
    private String receiverName;

    @NotNull
    private UUID receiverId; // 수령업체 UUID
    @NotNull
    private UUID productId; // 상품 UUID

    @NotBlank
    private String address; // 배송지: 물건을 수령할 주소

    @NotNull
    @Min(value = 1, message = "물품 요청 수량은 1개 이상이어야 합니다.")
    private Integer quantity; // 상품 수량

    @NotBlank
    private String message; // 요청사항

    public Order toEntity(UUID supplierId, Long userId, UUID deliveryId) {
        return Order.builder()
                .receiverId(receiverId)
                .productId(productId)
                .address(address)
                .quantity(quantity)
                .message(message)
                // .receiverSlackId(receiverSlackId)
                .status(Status.SUCCESS)
                .receiverName(receiverName)
                .supplierId(supplierId)
                .quantity(quantity)
                .userId(userId)
                .deliveryId(deliveryId)
                .build();
    }
}
