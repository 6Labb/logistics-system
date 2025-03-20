package com.sixlab.logistics.company_service.domain.repository;

import com.sixlab.logistics.company_service.domain.model.Company;

import java.util.List;
import java.util.UUID;

public interface CompanyRepositoryCustom {
    List<Company> searchCompanies(String name, String type, UUID hubId);
}
