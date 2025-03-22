package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.sixlab.logistics.slack_ai_service.Messenger.Exception.SlackCircuitException;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackSendRequestDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackSendResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackUserResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.ResponseMessageListDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackMessageInfoDto;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.MessageType;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.Slack;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.repository.SlackRepository;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.SlackApiClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {

    private final SlackApiClient SlackClient;
    private final SlackRepository slackRepository;

    public String getSlackIdByEmail(String email) {
        SlackUserResponseDto response = SlackClient.getUserByEmail(email);
        if (response.ok() && response.user() != null) {
            return response.user().id();
        }
        throw new SlackCircuitException(" 조회실패 : " + response.error());
    }

    @Retry(name = "slackRetry")
    @CircuitBreaker(name = "slackCircuitBreaker", fallbackMethod = "slackSendFallback")
    @Transactional
    public void sendSlackMessage(String email, SlackMessageInfoDto messageInfo) {
        String prompt = sendPromptMessage(messageInfo);

        String slackId = getSlackIdByEmail(email);
        if (slackId == null || slackId.contains("user_not_found")) {
            log.warn(" 유저 없음: {}", email);
            saveSlackHistory(null, prompt, MessageType.FAILED);
            return;
        }

        SlackSendRequestDto requestDto = new SlackSendRequestDto(slackId, prompt);

        SlackSendResponseDto response = SlackClient.sendMessage(requestDto);

        MessageType type = response.ok() ? MessageType.SENT : MessageType.FAILED;
        if (!response.ok()) {
            log.warn(" 슬랙 오류: {}", response.error());
        }

        saveSlackHistory(response.channel(), prompt, type);
    }
    public void slackSendFallback(String email, SlackMessageInfoDto messageInfo, Throwable t) {
        log.error(" 전송실패 k email: {}, error: {}", email, t.getMessage());

        String prompt = sendPromptMessage(messageInfo);
        saveSlackHistory(null, prompt, MessageType.FAILED);
    }
    
    private void saveSlackHistory(String channel, String content, MessageType type) {
        Slack slack = new Slack(channel, content, type);
        slackRepository.save(slack);
    }



    @Transactional(readOnly = true)
    public Page<ResponseMessageListDto> getSlackMessage(String keyword, int page, int size,  boolean isAsc) {
        Sort.Direction direction= isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, "createdAt");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ResponseMessageListDto> getAllMessageList = slackRepository.findAllSlackMessages(keyword,pageable,isAsc);
        return getAllMessageList;
    }


    @Transactional
    public void deletedMessage(UUID id) {
        Slack slack = slackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("email을 찾을수 없음"+id));
        slack.deletedMessage(id);
    }

    private String sendPromptMessage(SlackMessageInfoDto messageInfo) {
        return String.format(
                "*🚛 배송 알림!*\n\n" +
                        "주문 번호: *%s*\n" +
                        "주문자 정보: %s\n" +
                        "상품 정보: %s %d\n" +
                        "요청 사항: %s\n\n" +
                        "📦 *배송 정보*\n" +
                        "발송지: %s\n" +
                        "경유지: %s\n" +
                        "도착지: %s\n\n" +
                        "🛠 배송 담당자: %s\n\n" +
                        "⏳ *최종 발송 시한:* *%s*",
                messageInfo.getOrderId(),
                messageInfo.getCustomerName(),
                messageInfo.getProductName(),
                messageInfo.getQuantity(),
                messageInfo.getRequest(),
                messageInfo.getSender(),
                String.join(", ", messageInfo.getTransitCenters()),
                messageInfo.getDestination(),
                messageInfo.getDeliveryManagerName(),
                messageInfo.getDeadline()
        );
    }

}
