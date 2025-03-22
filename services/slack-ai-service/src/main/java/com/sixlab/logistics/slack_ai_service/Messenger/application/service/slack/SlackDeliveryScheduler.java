package com.sixlab.logistics.slack_ai_service.Messenger.application.service.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackDeliveryScheduler {

    private final SlackDeliveryService slackDeliveryService;
    private final RedissonClient redissonClient;
    private final String REDIS_KEY_PREFIX = "lock:slack:Directions:";

    @Scheduled(cron = "0 0 6 * * *")
    public void sendDailySlack() {
        RLock lock = redissonClient.getLock(REDIS_KEY_PREFIX);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS); //5초동안 락 r
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            slackDeliveryService.sendScheduledMessages();
        }
    /*
        redis RockUp
        모든서버는 redis를 바라봄
        동시에 스케쥴링을 시도하지만 가장 먼저 성공한 서버에만 rock Key 부여
        나머지 서버는 작업종료
    */
    }
}
