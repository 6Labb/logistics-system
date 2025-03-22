package com.sixlab.logistics.hub_service.hub.domain.model;

import java.util.UUID;

@Entity
public class HubRoute {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_hub_id")
    private Hub departureHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_hub_id")
    private Hub arrivalHub;

    private int distance;
    private int duration;

    // Getter, Constructor, etc.
}
