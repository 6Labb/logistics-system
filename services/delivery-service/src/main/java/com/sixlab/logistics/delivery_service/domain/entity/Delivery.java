package com.sixlab.logistics.delivery_service.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.delivery_service.application.dto.DeliveryRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "p_delivery")
public class Delivery extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(length = 255, nullable = false)
    private String deliveryAddress;

    @Column(length = 50, nullable = false)
    private String receiveName;

    @Column(length = 50, nullable = false)
    private String receiveSlackId;

    private UUID fromHubId;

    private UUID toHubId;

    private UUID orderId;

    private UUID companyDeliveryAgentId;

    @Builder
    public Delivery(DeliveryRequestDto requestDto, UUID orderId, UUID fromHubId, UUID toHubId) {
        this.status = DeliveryStatus.WAITING;
        this.deliveryAddress = requestDto.getDeliveryAddress();
        this.receiveName = requestDto.getReceiveName();
        this.companyDeliveryAgentId = requestDto.getCompanyDeliveryAgentId();
        this.orderId = orderId;
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
    }

    public void updateDelivery(DeliveryRequestDto requestDto) {
        this.deliveryAddress = requestDto.getDeliveryAddress();
    }
    public void updateDeliveryStatus(DeliveryStatus status) {
        this.status = status;
    }
}
