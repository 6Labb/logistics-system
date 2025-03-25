package com.sixlab.logistics.company_service.domain.repository;

import com.sixlab.logistics.company_service.domain.model.Company;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>, CompanyRepositoryCustom {
    boolean existsByName(String name);

    @Query("SELECT c.hubId FROM Company c WHERE c.id = :companyId")
    Optional<UUID> findHubIdByCompanyId(@Param("companyId") UUID companyId);
}
