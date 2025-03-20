package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.company_service.domain.model.CompanyType;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import com.sixlab.logistics.company_service.presentation.dto.PaginationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    CompanyResponseDto createCompany(CompanyRequestDto requestDto);
    PaginationResponseDto<CompanyResponseDto> getAllCompanies(int page, int size);
    Page<CompanyResponseDto> searchCompanies(String name, String type, UUID hubId, Pageable pageable);
    CompanyResponseDto getCompanyById(UUID companyId);
    CompanyResponseDto updateCompany(UUID companyId, CompanyRequestDto requestDto);
    void deleteCompany(UUID companyId);
}
