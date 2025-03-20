package com.sixlab.logistics.company_service.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.model.CompanyType;
import com.sixlab.logistics.company_service.domain.model.QCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Company> searchCompanies(String name, String type, UUID hubId) {
        QCompany company = QCompany.company;

        return queryFactory
                .selectFrom(company)
                .where(
                        name != null ? company.name.containsIgnoreCase(name) : null,
                        type != null ? company.type.eq(CompanyType.valueOf(type)) : null,
                        hubId != null ? company.hubId.eq(hubId) : null
                )
                .fetch();
    }
}
