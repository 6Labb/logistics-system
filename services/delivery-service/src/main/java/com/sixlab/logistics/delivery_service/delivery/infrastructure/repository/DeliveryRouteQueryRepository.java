package com.sixlab.logistics.delivery_service.delivery.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteSearchDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.QDelivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.QDeliveryRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<DeliveryRouteResponseDto> searchDeliveryRouteList(DeliveryRouteSearchDto searchDto, Pageable pageable) {

        QDeliveryRoute deliveryRoute = QDeliveryRoute.deliveryRoute;

        // 검색 조건 구성
        BooleanBuilder builder = new BooleanBuilder();

        // deliveryId로 검색
        if (searchDto.getDeliveryId() != null) {
            builder.and(deliveryRoute.deliveryId.eq(searchDto.getDeliveryId()));
        }

        // receiveName으로 검색
        if (searchDto.getDeliveryAgentId() != null) {
            builder.and(deliveryRoute.companyDeliveryAgentId.eq(searchDto.getDeliveryAgentId()));
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