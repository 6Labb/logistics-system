package com.sixlab.logistics.company_service.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {
    private UUID id;
    private String name;
    private String address;
    private String type;
    private UUID hubId;
    private LocalDateTime createdAt;
    private String createdBy;
}
