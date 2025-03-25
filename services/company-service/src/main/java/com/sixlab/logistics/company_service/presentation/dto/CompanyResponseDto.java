package com.sixlab.logistics.company_service.presentation.dto;

import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.model.CompanyType;
import lombok.*;

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
    private CompanyType type;
    private UUID hubId;
    private LocalDateTime createdAt;

    @Builder
    public CompanyResponseDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.type = company.getType();
        this.hubId = company.getHubId();
        this.createdAt = company.getCreatedAt();
    }

    public CompanyResponseDto(UUID id, String name, String address, CompanyType type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

}
