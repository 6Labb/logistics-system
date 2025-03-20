package com.sixlab.logistics.company_service.domain.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.model.CompanyType;
import com.sixlab.logistics.company_service.domain.model.QCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Company> searchCompanies(String name, String type, UUID hubId, Pageable pageable) {
        QCompany company = QCompany.company;

        JPAQuery<Company> query = queryFactory
                .selectFrom(company)
                .where(
                        name != null ? company.name.containsIgnoreCase(name) : null,
                        type != null ? company.type.eq(CompanyType.valueOf(type)) : null,
                        hubId != null ? company.hubId.eq(hubId) : null
                );

        // 전체 카운트 조회
        long total = query.fetchCount();

        // 페이지네이션 적용
        List<Company> companies = query
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch();  // 결과 리스트 조회

        // PageImpl 사용하여 페이지네이션 정보 포함
        return new PageImpl<>(companies, pageable, total);
    }
}
