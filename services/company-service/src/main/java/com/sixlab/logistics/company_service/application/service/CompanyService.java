package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    // 업체 등록
    public CompanyResponseDto createCompany(CompanyRequestDto request) {
        return null;
    }

    public List<CompanyResponseDto> getAllCompanies() {
        return null;
    }

    public CompanyResponseDto getCompanyById(UUID companyId) {
        return null;
    }

    public CompanyResponseDto updateCompany(UUID companyId) {
        return null;
    }

    public void deleteCompany(UUID companyId) {
    }
}
