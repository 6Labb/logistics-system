package com.sixlab.logistics.slack_ai_service.Messenger.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "delivery_notifications")
public class DeliveryNotification extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    // 알림 대상 (담당자 ID, 혹은 슬랙 채널 ID 등)
    private String target;

    // 알림 내용
    @Column(columnDefinition = "TEXT")
    private String message;

    // 예약 전송 시간
    private LocalDateTime sendTime;

    @Enumerated(EnumType.STRING)
    private boolean sent;

}