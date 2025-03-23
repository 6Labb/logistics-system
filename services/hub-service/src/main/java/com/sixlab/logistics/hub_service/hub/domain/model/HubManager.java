package com.sixlab.logistics.hub_service.hub.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
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
@Table(name = "p_hub_manager")
public class HubManager extends BasicEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private UUID hubId;

    @Builder
    private HubManager(Long userId, UUID hubId) {
        this.userId = userId;
        this.hubId = hubId;
    }

    public static HubManager create(Long userId, UUID hubId) {
        return HubManager.builder()
                .userId(userId)
                .hubId(hubId)
                .build();
    }

    public void update(UUID hubId) {
        this.hubId = hubId;
    }


}
