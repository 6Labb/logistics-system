package com.sixlab.logistics.hub_service.hub.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub_route",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"departure_hub_id", "arrival_hub_id"})
        })
public class HubRoute extends BasicEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_hub_id")
    private Hub departureHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_hub_id")
    private Hub arrivalHub;

    private double distance;

    private int duration;

    @Builder
    private HubRoute(Hub departureHub, Hub arrivalHub, double distance, int duration) {
        this.departureHub = departureHub;
        this.arrivalHub = arrivalHub;
        this.distance = distance;
        this.duration = duration;
    }

    public static HubRoute create(Hub departureHub, Hub arrivalHub, double distance, int duration) {
        return HubRoute.builder()
                .departureHub(departureHub)
                .arrivalHub(arrivalHub)
                .distance(distance)
                .duration(duration)
                .build();
    }

    public void update(Hub departureHub, Hub arrivalHub, double distance, int duration) {
        this.departureHub = departureHub;
        this.arrivalHub = arrivalHub;
        this.distance = distance;
        this.duration = duration;
    }

}
