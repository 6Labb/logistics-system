package com.sixlab.logistics.hub_service.hub;


import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.hub_service.hubmanager.HubManager;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

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

    //private Long hubManagerId;


    @Builder
    private Hub(String hubName, String hubAddress, double latitude, double longitude) {
        this.hubName = hubName;
        this.hubAddress = hubAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Hub create(String hubName, String hubAddress, double latitude, double longitude) {
        return Hub.builder()
                .hubName(hubName)
                .hubAddress(hubAddress)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
