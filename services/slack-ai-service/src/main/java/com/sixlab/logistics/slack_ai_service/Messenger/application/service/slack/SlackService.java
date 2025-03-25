package com.sixlab.logistics.slack_ai_service.Messenger.application.service.slack;

import com.sixlab.logistics.common.shared.exception.InternalServerException;
import com.sixlab.logistics.common.shared.exception.InvalidParameterException;
import com.sixlab.logistics.common.shared.exception.OperationNotAllowedException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.*;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.MessageType;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.Slack;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.repository.SlackRepository;
import com.sixlab.logistics.slack_ai_service.Messenger.exception.SlackCircuitException;
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
        try {
            SlackUserResponseDto response = SlackClient.getUserByEmail(email);
            if (response.ok() && response.user() != null) {
                return response.user().id();
            }
            throw new SlackCircuitException("조회 실패: " + response.error());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Slack 사용자 조회 실패: " + email, e);
        }
    }

    @Retry(name = "slackRetry")
    @CircuitBreaker(name = "slackCircuitBreaker", fallbackMethod = "slackSendFallback")
    @Transactional
    public void sendSlackMessage(String email, SlackMessageInfoDto messageInfo) {
        if (email == null || email.isEmpty()) {
            throw new InvalidParameterException("이메일이 비어있습니다.");
        }
        if (messageInfo == null) {
            throw new InvalidParameterException("Slack 메시지 정보가 null입니다.");
        }

        try {
            String prompt = sendPromptMessage(messageInfo);

            String slackId = getSlackIdByEmail(email);
            if (slackId == null || slackId.contains("user_not_found")) {
                log.warn("유저 없음: {}", email);
                saveSlackHistory(null, prompt, MessageType.FAILED);
                return;
            }

            SlackSendRequestDto requestDto = new SlackSendRequestDto(slackId, prompt);

            SlackSendResponseDto response = SlackClient.sendMessage(requestDto);

            MessageType type = response.ok() ? MessageType.SENT : MessageType.FAILED;
            if (!response.ok()) {
                log.warn("슬랙 오류: {}", response.error());
                throw new OperationNotAllowedException("Slack 메시지 전송 실패: " + response.error());
            }

            saveSlackHistory(response.channel(), prompt, type);
        } catch (SlackCircuitException e) {
            log.error("Slack 회로 차단 오류: {}", e.getMessage(), e);
            saveSlackHistory(null, sendPromptMessage(messageInfo), MessageType.FAILED);
        } catch (Exception e) {
            log.error("Slack 메시지 전송 중 오류 발생: {}", e.getMessage(), e);
            throw new InternalServerException("Slack 메시지 전송 중 서버 오류 발생");
        }
    }
    public void slackSendFallback(String email, SlackMessageInfoDto messageInfo, Throwable t) {
        log.error(" 전송실패 email: {}, error: {}", email, t.getMessage());

        String prompt = sendPromptMessage(messageInfo);
        saveSlackHistory(null, prompt, MessageType.FAILED);
    }

    private void saveSlackHistory(String channel, String content, MessageType type) {
        if (content == null) {
            throw new InvalidParameterException("저장할 메시지 내용이 null 입니다.");
        }
        try {
            Slack slack = new Slack(channel, content, type);
            slackRepository.save(slack);
        } catch (Exception e) {
            log.error("Slack 히스토리 저장 실패: {}", e.getMessage(), e);
            throw new InternalServerException("Slack 저장 중 서버 오류 발생");
        }
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
