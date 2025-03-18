package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    CompanyResponseDto createCompany(CompanyRequestDto requestDto);
    List<CompanyResponseDto> getAllCompanies();
    CompanyResponseDto getCompanyById(UUID companyId);
    CompanyResponseDto updateCompany(UUID companyId, CompanyRequestDto requestDto);
    void deleteCompany(UUID companyId);
}
