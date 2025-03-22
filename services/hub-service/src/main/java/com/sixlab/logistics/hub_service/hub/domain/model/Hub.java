package com.sixlab.logistics.hub_service.hub.domain.model;


import com.sixlab.logistics.common.shared.domain.BasicEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_hub")
public class Hub extends BasicEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String hubName;

    private String hubAddress;

    private double latitude;

    private double longitude;

    private Long hubManagerId;

    @OneToMany(mappedBy = "departureHub")
    private List<HubRoute> departureRoutes = new ArrayList<>();

    @OneToMany(mappedBy = "arrivalHub")
    private List<HubRoute> arrivalRoutes = new ArrayList<>();


    @Builder
    private Hub(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerId) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerId = hubManagerId;
    }

    public static Hub create(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerId) {
        return Hub.builder()
                .hubName(hubName)
                .hubAddress(hubAddress)
                .latitude(latitude)
                .longitude(longitude)
                .hubManagerId(hubManagerId)
                .build();
    }

    public void update(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerId) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerId = hubManagerId;
    }



}
