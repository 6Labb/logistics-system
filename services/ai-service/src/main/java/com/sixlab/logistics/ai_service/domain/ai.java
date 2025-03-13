package com.sixlab.logistics.ai_service.domain;

import com.sparta.common.domain.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class ai extends BasicEntity {
    @Id
    private UUID id;
}
