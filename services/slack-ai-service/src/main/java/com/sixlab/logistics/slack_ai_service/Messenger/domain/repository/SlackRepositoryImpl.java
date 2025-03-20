package com.sixlab.logistics.slack_ai_service.Messenger.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ResponseMessageListDto;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.QSlack;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class SlackRepositoryImpl implements SlackRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QSlack slack = QSlack.slack;

    @Override
    public Page<ResponseMessageListDto> findAllSlackMessages(String keyword,Pageable pageable,boolean isAsc) {
        List<ResponseMessageListDto> response = jpaQueryFactory
                .select(Projections.constructor(ResponseMessageListDto.class,
                        slack.id,
                        slack.slackId,
                        slack.message,
                        slack.messageStatus.stringValue()
                ))
                .from(slack)
                .where(containsKeyword(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(isAsc ? slack.updatedAt.asc() : slack.updatedAt.desc())
                .fetch();
        JPAQuery<Long> query = getTotalCount(keyword);

        return PageableExecutionUtils.getPage(response, pageable, () -> Optional.ofNullable(query.fetchOne()).orElse(0L));
    }

    JPAQuery<Long> getTotalCount(String keyword) {
        return jpaQueryFactory
                .select(slack.count())
                .from(slack)
                .where(containsKeyword(keyword));
    }

    private BooleanExpression containsKeyword(String keyword) {
        return (keyword != null && !keyword.isEmpty()) ?
                slack.slackId.contains(keyword).or(slack.message.contains(keyword)) : null;
    }

}
