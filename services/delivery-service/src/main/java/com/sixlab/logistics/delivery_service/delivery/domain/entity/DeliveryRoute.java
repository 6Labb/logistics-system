package com.sixlab.logistics.delivery_service.delivery.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "p_delivery_route")
public class DeliveryRoute extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer sequence;

    @Column(name = "estimated_distance", nullable = false)
    private Double estimatedDistance;

    @Column(name = "estimated_time", nullable = false)
    private Integer estimatedTime;

    @Column(name = "actual_distance", nullable = false)
    private Double actualDistance;

    @Column(name = "actual_time", nullable = false)
    private Integer actualTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryRouteStatus status;

    private UUID deliveryId;

    private UUID fromHubId;

    private UUID toHubId;

    private Long deliveryAgentId;

    @Builder
    public DeliveryRoute(Integer sequence,
                         Double estimatedDistance,
                         Integer estimatedTime,
                         Double actualDistance,
                         Integer actualTime,
                         DeliveryRouteStatus status,
                         UUID deliveryId,
                         UUID fromHubId,
                         UUID toHubId,
                         Long deliveryAgentId
                         ) {
        this.sequence = 0;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
        this.status = DeliveryRouteStatus.WAITING;
        this.deliveryId = UUID.randomUUID();
        this.fromHubId = UUID.randomUUID();
        this.toHubId = UUID.randomUUID();
        this.deliveryAgentId = deliveryAgentId;
    }

    public void updateDeliveryRoute(DeliveryRouteRequestDto requestDto) {
        this.actualDistance = requestDto.getActualDistance();
        this.actualTime = requestDto.getActualTime();
        this.estimatedDistance = requestDto.getEsimateDistance();
        this.estimatedTime = requestDto.getEsimateTime();
        this.deliveryAgentId = requestDto.getDeliveryAgentId();
    }

    public void updateDeliveryRouteStatus(DeliveryRouteStatus status) {
        this.status = status;
    }

}
