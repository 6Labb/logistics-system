package com.sixlab.logistics.order_service.application.dto.response;

import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.domain.model.Status;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderCreateResponseDto {
    private final UUID supplierId; // 공급업체 UUID
    private final UUID receiverId; // 수령업체 UUID
    private final UUID deliveryId; // 배달 UUID
    private final String address; // 배송지 ( 물품을 수령받을 최종 주소, 허브 주소가 아님 )
    private final UUID productId; // (주문) 물품 UUID
    private final Integer quantity; // (주문) 물품 수량
    private final String message; // 요청사항
    private final Status status; // 주문 상태
    private final UUID userId; // 주문자 고유 id

    public OrderCreateResponseDto(Order order) {
        this.supplierId = order.getSupplierId();
        this.receiverId = order.getReceiverId();
        this.deliveryId = order.getDeliveryId();
        this.address = order.getAddress();
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.message = order.getMessage();
        this.status = order.getStatus();
        this.userId = order.getUserId();
    }




}
