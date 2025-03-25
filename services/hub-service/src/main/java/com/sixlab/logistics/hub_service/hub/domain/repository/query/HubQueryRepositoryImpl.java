package com.sixlab.logistics.hub_service.hub.domain.repository.query;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.model.QHub;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HubQueryRepositoryImpl implements HubQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Hub> searchHubs(String keyword, String sort, String order, Pageable pageable) {
        QHub hub = QHub.hub;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(hub.deletedAt.isNull());

        if (keyword != null && !keyword.isBlank()) {
            builder.and(
                    hub.hubName.containsIgnoreCase(keyword)
                            .or(hub.hubAddress.containsIgnoreCase(keyword))
            );
        }

        if (!sort.equals("hubName")) {
            sort = "hubName";
        }

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                order.equalsIgnoreCase("desc") ? Order.DESC : Order.ASC,
                Expressions.stringPath(hub, sort)
        );

        List<Hub> content = jpaQueryFactory
                .selectFrom(hub)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(hub.count())
                .from(hub)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}
