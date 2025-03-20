package com.sixlab.logistics.slack_ai_service.Messenger.domain.entity;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Slack extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "slackId", nullable = false)
    private String slackId;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name="message_status")
    private MessageType messageStatus;

    public Slack(String slackId,String message ,MessageType messageStatus) {
        this.slackId = slackId;
        this.message = message;
        this.messageStatus = messageStatus;
    }
    public void deletedMessage(UUID id) {
        super.delete(deletedBy);
    }
}