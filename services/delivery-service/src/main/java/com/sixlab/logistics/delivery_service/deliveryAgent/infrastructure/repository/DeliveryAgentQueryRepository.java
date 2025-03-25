package com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.QDeliveryAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryAgentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<DeliveryAgentResponseDto> searchDeliveryAgentList(DeliveryAgentSearchDto searchDto, Pageable pageable) {

        QDeliveryAgent deliveryAgent = QDeliveryAgent.deliveryAgent;

        // 검색 조건 구성
        BooleanBuilder builder = new BooleanBuilder();

        // 타입으로 검색
        if (searchDto.getType() != null) {
            builder.and(deliveryAgent.type.eq(searchDto.getType()));
        }

        // 허브 ID로 검색
        if (searchDto.getHubId() != null) {
            builder.and(deliveryAgent.hubId.eq(searchDto.getHubId()));
        }

        // 페이징된 결과 조회
        List<DeliveryAgentResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        DeliveryAgentResponseDto.class,
                        deliveryAgent.userId,
                        deliveryAgent.type,
                        deliveryAgent.deliverySequence,
                        deliveryAgent.hubId,
                        deliveryAgent.slackId
                ))
                .from(deliveryAgent)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 결과 수 조회
        long total = jpaQueryFactory
                .select(deliveryAgent.count())
                .from(deliveryAgent)
                .where(builder)
                .fetchOne() != null ? jpaQueryFactory
                .select(deliveryAgent.count())
                .from(deliveryAgent)
                .where(builder)
                .fetchOne() : 0L;

        return new PageImpl<>(content, pageable, total);
    }
}