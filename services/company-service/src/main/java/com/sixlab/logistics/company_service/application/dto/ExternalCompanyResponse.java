package com.sixlab.logistics.company_service.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ExternalCompanyResponse {
    private UUID id;
    private String name;
    private String address;
    private UUID hubId;

    @Builder
    public ExternalCompanyResponse(UUID id, String name, String address, UUID hubId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.hubId = hubId;
    }

}
