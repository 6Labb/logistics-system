package com.sixlab.logistics.delivery_service.delivery.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteSearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.model.QDeliveryRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<DeliveryRouteResponseDto> searchDeliveryRouteList(DeliveryRouteSearchDto searchDto, Pageable pageable, Long deliveryAgentId, UUID ownHubId) {

        QDeliveryRoute deliveryRoute = QDeliveryRoute.deliveryRoute;

        // 검색 조건 구성
        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건 for - Long companyDeliveryAgentId;
        if (searchDto.getCompanyDeliveryAgentId() != null) {
            builder.and(deliveryRoute.companyDeliveryAgentId.eq(searchDto.getCompanyDeliveryAgentId()));
        }

        // 검색 조건 for - Long hubDeliveryAgentId;
        if (searchDto.getHubDeliveryAgentId() != null) {
            builder.and(deliveryRoute.hubDeliveryAgentId.eq(searchDto.getHubDeliveryAgentId()));
        }

        // 검색 조건 for - Long deliveryAgentId
        if (deliveryAgentId != null) {
            builder.and(deliveryRoute.hubDeliveryAgentId.eq(deliveryAgentId).or(deliveryRoute.companyDeliveryAgentId.eq(deliveryAgentId)));
        }

        // 검색 조건 for - UUID ownHubId
        if (ownHubId != null) {
            builder.and(deliveryRoute.fromHubId.eq(ownHubId).or(deliveryRoute.toHubId.eq(ownHubId)));
        }

        // deliveryId로 검색
        if (searchDto.getDeliveryId() != null) {
            builder.and(deliveryRoute.deliveryId.eq(searchDto.getDeliveryId()));
        }

        // 페이징된 결과 조회
        List<DeliveryRouteResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        DeliveryRouteResponseDto.class,
                        deliveryRoute.id,
                        deliveryRoute.sequence,
                        deliveryRoute.estimatedDistance,
                        deliveryRoute.estimatedTime,
                        deliveryRoute.actualDistance,
                        deliveryRoute.actualTime,
                        deliveryRoute.status,
                        deliveryRoute.deliveryId,
                        deliveryRoute.fromHubId,
                        deliveryRoute.toHubId,
                        deliveryRoute.companyDeliveryAgentId,
                        deliveryRoute.hubDeliveryAgentId
                ))
                .from(deliveryRoute)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 결과 수 조회
        long total = jpaQueryFactory
                .select(deliveryRoute.count())
                .from(deliveryRoute)
                .where(builder)
                .fetchOne() != null ? jpaQueryFactory
                .select(deliveryRoute.count())
                .from(deliveryRoute)
                .where(builder)
                .fetchOne() : 0L;

        return new PageImpl<>(content, pageable, total);
    }
}