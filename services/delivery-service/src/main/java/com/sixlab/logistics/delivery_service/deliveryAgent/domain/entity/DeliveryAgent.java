package com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRequestDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "p_delivery_agent")
public class DeliveryAgent extends BasicEntity {

    @Id
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryAgentType type;

    private Integer deliverySequence;

    private UUID hubId;

    @Column(length = 255, nullable = false)
    private String slackId;

    @Builder
    public DeliveryAgent(DeliveryAgentRequestDto requestDto) {
        super();
        this.userId = requestDto.getUserId();
        this.type = requestDto.getType();
        this.deliverySequence = requestDto.getDeliverySequence();
        this.hubId = UUID.randomUUID();
        this.slackId = requestDto.getSlackId();
    }

    @Builder
    public DeliveryAgent(DeliveryAgentResponseDto responseDto) {
        super();
        this.userId = responseDto.getUserId();
        this.type = responseDto.getType();
        this.deliverySequence = responseDto.getDeliverySequence();
        this.hubId = responseDto.getHubId();
        this.slackId = responseDto.getSlackId();
    }

    public void updateDeliveryAgent(DeliveryAgentRequestDto requestDto) {
        this.hubId = requestDto.getHubId();
        this.slackId = requestDto.getSlackId();
    }

    public void updateSequence(Integer sequence) {
        this.deliverySequence = sequence;
    }

}
