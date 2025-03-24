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

    private Long hubManagerUserId;


    private Hub(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerUserId) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerUserId = hubManagerUserId;
    }

    @Builder
    public static Hub create(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerUserId) {
        return new Hub(hubName, hubAddress, latitude, longitude, hubManagerUserId);
    }

    public void update(String hubName, String hubAddress, double latitude, double longitude, Long hubManagerUserId) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hubManagerUserId = hubManagerUserId;
    }



}
