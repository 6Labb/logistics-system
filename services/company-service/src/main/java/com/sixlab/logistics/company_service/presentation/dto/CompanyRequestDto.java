package com.sixlab.logistics.company_service.presentation.dto;

import com.sixlab.logistics.company_service.domain.model.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {
    private String name;
    private CompanyType type;
    private String address;
    private UUID hubId;

}
