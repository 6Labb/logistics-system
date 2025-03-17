package com.sixlab.logistics.company_service.domain.repository;

import com.sixlab.logistics.company_service.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByName(String name);
}
