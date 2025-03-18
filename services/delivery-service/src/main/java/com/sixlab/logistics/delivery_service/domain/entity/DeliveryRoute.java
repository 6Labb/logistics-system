package com.sixlab.logistics.delivery_service.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
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

    private UUID departureHubId;

    private UUID arrivalHubId;

    private UUID deliveryAgentId;

    @Builder
    public DeliveryRoute(Integer sequence,
                         Double estimatedDistance,
                         Integer estimatedTime,
                         Double actualDistance,
                         Integer actualTime,
                         DeliveryRouteStatus status,
                         UUID deliveryId,
                         UUID departureHubId,
                         UUID arrivalHubId,
                         UUID deliveryAgentId
                         ) {
        this.sequence = sequence;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
        this.status = status;
        this.deliveryId = UUID.randomUUID();;
        this.departureHubId = UUID.randomUUID();;
        this.arrivalHubId = UUID.randomUUID();;
        this.deliveryAgentId = UUID.randomUUID();;
    }

}
