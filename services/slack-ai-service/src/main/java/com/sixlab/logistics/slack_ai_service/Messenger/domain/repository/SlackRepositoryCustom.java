package com.sixlab.logistics.slack_ai_service.Messenger.domain.repository;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ResponseMessageListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackRepositoryCustom {
    Page<ResponseMessageListDto> findAllSlackMessages(String keyword,Pageable pageable,boolean isAsc);
}
