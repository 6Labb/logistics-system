package com.sixlab.logistics.slack_ai_service.Messenger.application.service.slack;

import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.SlackApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackDeliveryService {

    private final SlackApiClient slackApiClient;
    //분산 rock 사용

    @Transactional
    public void sendScheduledMessages() {
    }
}
