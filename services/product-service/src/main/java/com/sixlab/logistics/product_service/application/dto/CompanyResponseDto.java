package com.sixlab.logistics.product_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanyResponseDto {
    private UUID companyId;
    private UUID hubId;
}
