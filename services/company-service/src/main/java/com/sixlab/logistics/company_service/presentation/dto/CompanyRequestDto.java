package com.sixlab.logistics.company_service.presentation.dto;

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
    private String type;
    private String address;
    private UUID hubId;

}
