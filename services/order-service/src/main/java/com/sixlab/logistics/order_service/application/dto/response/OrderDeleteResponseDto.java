package com.sixlab.logistics.order_service.application.dto.response;

import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDeleteResponseDto {
        private UUID orderId; // 주문 id
        private UUID supplierId; // 공급업체 id
        private UUID receiverId; // 수령업체 id
        private UUID productId; // 상품 id
        private Integer quantity; // 상품 수량
        private String message; // 요청사항
        private UUID deliveryId; // 배송 id
        private Status status; // 주문 상태
        private Long userId;
        private String receiverName; // 수령인
        private Long deletedBy;
        private LocalDateTime deletedAt;

        public OrderDeleteResponseDto(Order order) {
            this.orderId = order.getOrderId();
            this.supplierId = order.getSupplierId();
            this.receiverId = order.getReceiverId();
            this.productId = order.getProductId();
            this.quantity = order.getQuantity();
            this.message = order.getMessage();
            this.deliveryId = order.getDeliveryId();
            this.status = order.getStatus();
            this.userId = order.getUserId();
            this.receiverName = order.getReceiverName();
            this.deletedBy = order.getDeletedBy();
            this.deletedAt = order.getDeletedAt();
        }
}
