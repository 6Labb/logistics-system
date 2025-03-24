package com.sixlab.logistics.delivery_service.delivery.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.model.QDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<DeliveryResponseDto> searchDeliveryList(DeliverySearchDto searchDto, Pageable pageable, Long deliveryAgentId, UUID ownHubId) {

        QDelivery delivery = QDelivery.delivery;

        // 검색 조건 구성
        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건 for - Long companyDeliveryAgentId;
        if (searchDto.getCompanyDeliveryAgentId() != null) {
            builder.and(delivery.companyDeliveryAgentId.eq(searchDto.getCompanyDeliveryAgentId()));
        }

        // 검색 조건 for - Long hubDeliveryAgentId;
        if (searchDto.getHubDeliveryAgentId() != null) {
            builder.and(delivery.hubDeliveryAgentId.eq(searchDto.getHubDeliveryAgentId()));
        }

        // 검색 조건 for - String receiveName;
        if (searchDto.getReceiveName() != null) {
            builder.and(delivery.receiveName.eq(searchDto.getReceiveName()));
        }

        // 검색 조건 for - Long deliveryAgentId
        if (deliveryAgentId != null) {
            builder.and(delivery.hubDeliveryAgentId.eq(deliveryAgentId).or(delivery.companyDeliveryAgentId.eq(deliveryAgentId)));
        }

        // 검색 조건 for - UUID ownHubId
        if (ownHubId != null) {
            builder.and(delivery.fromHubId.eq(ownHubId).or(delivery.toHubId.eq(ownHubId)));
        }


        // 페이징된 결과 조회
        List<DeliveryResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        DeliveryResponseDto.class,
                        delivery.id,
                        delivery.status,
                        delivery.deliveryAddress,
                        delivery.receiveName,
                        delivery.companyDeliveryAgentId,
                        delivery.hubDeliveryAgentId,
                        delivery.fromHubId,
                        delivery.toHubId
                ))
                .from(delivery)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 결과 수 조회
        long total = jpaQueryFactory
                .select(delivery.count())
                .from(delivery)
                .where(builder)
                .fetchOne() != null ? jpaQueryFactory
                .select(delivery.count())
                .from(delivery)
                .where(builder)
                .fetchOne() : 0L;

        return new PageImpl<>(content, pageable, total);
    }
}