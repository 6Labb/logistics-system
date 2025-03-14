package com.sixlab.logistics.company_service;

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
