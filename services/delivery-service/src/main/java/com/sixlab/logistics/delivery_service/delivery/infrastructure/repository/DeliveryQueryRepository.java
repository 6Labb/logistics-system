package com.sixlab.logistics.delivery_service.delivery.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.QDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<DeliveryResponseDto> searchDeliveryList(DeliverySearchDto searchDto, Pageable pageable) {

        QDelivery delivery = QDelivery.delivery;

        // 검색 조건 구성
        BooleanBuilder builder = new BooleanBuilder();

        // deliveryId로 검색
        if (searchDto.getDeliveryAgentId() != null) {
            builder.and(delivery.deliveryAgentId.eq(searchDto.getDeliveryAgentId()));
        }

        // receiveName으로 검색
        if (searchDto.getReceiveName() != null) {
            builder.and(delivery.receiveName.eq(searchDto.getReceiveName()));
        }

        // 페이징된 결과 조회
        List<DeliveryResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        DeliveryResponseDto.class,
                        delivery.id,
                        delivery.status,
                        delivery.deliveryAddress,
                        delivery.receiveName,
                        delivery.deliveryAgentId,
                        delivery.hubRouteId,
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