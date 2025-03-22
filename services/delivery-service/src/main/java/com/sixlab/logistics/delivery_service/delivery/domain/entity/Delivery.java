package com.sixlab.logistics.delivery_service.delivery.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRequestDto;
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

    private UUID fromHubId;

    private UUID toHubId;

    private Long deliveryAgentId;

    @Builder
    public Delivery(DeliveryRequestDto requestDto, UUID fromHubId, UUID toHubId, Long deliveryAgentId, UUID id) {
        super();
        this.id = id;
        this.status = DeliveryStatus.WAITING;
        this.deliveryAddress = requestDto.getDeliveryAddress();
        this.receiveName = requestDto.getReceiveName();
        this.deliveryAgentId = deliveryAgentId;
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
    }

    @Builder
    public Delivery(DeliveryRequestDto requestDto, UUID fromHubId, UUID toHubId, UUID id) {
        super();
        this.id = id;
        this.status = DeliveryStatus.WAITING;
        this.deliveryAddress = requestDto.getDeliveryAddress();
        this.receiveName = requestDto.getReceiveName();
        this.fromHubId = fromHubId;
        this.toHubId = toHubId;
    }

    public void updateDeliveryAgent(Long deliveryAgentId) {
        this.deliveryAgentId = deliveryAgentId;
    }

    public void updateDelivery(DeliveryRequestDto requestDto) {
        this.deliveryAddress = requestDto.getDeliveryAddress();
    }

    public void updateDeliveryStatus(DeliveryStatus status) {
        this.status = status;
    }

}
