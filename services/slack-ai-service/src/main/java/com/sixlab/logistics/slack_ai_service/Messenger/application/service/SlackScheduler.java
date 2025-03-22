package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackScheduler {

    private final SlackSchedulerService slackSchedulerService;

    @Scheduled(cron = "0 0 6 * * *")
    public void sendDailySlack() {
        slackSchedulerService.sendScheduledMessages();
    }
}
