package com.sixlab.logistics.company_service.domain.repository;

import com.sixlab.logistics.company_service.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
// QueryDSL 사용
public interface CompanyRepositoryCustom {
    Page<Company> searchCompanies(String name, String type, UUID hubId, Pageable pageable);
}
