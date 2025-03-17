package com.sixlab.logistics.order_service.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name="p_order") // 1. p_order 인지 p_orders 인지 확인할 것
@Getter
public class Order extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID orderId; // 주문을 식별하는 고유 UUID

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId; // 공급업체 UUID

    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId; // 수령업체 UUID

    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId; // 배달 UUID

    @Column(nullable = false)
    private String address; // 배송지 ( 물품을 수령받을 최종 주소, 허브 주소가 아님 )

    @Column(name = "product_id", nullable = false)
    private UUID productId; // (주문) 물품 UUID

    @Column(nullable = false)
    private Integer quantity; // (주문) 물품 수량

    @Column(nullable = false)
    private String message; // 요청사항

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // 주문 상태

    @Column(name = "user_id")
    private UUID userId; // 주문자 고유 id
}
