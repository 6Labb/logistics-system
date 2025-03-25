package com.sixlab.logistics.user_service.user.domain.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.user_service.user.domain.model.QUser;
import com.sixlab.logistics.user_service.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> searchUsers(String keyword, String sort, String order, Pageable pageable) {
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.deletedAt.isNull());

        if (keyword != null && !keyword.isEmpty()) {
            builder.and(user.username.containsIgnoreCase(keyword));
        }

        PathBuilder<User> entityPath = new PathBuilder<>(User.class, "user");
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                order.equalsIgnoreCase("desc") ? Order.DESC : Order.ASC,
                entityPath.getString(sort)
        );

        List<User> content = queryFactory
                .selectFrom(user)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(user.count())
                .from(user)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);

    }


}

