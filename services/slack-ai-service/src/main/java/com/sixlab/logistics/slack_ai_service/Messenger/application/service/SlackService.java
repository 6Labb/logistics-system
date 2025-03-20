package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.sixlab.logistics.common.shared.dto.SlackSendRequestDto;
import com.sixlab.logistics.common.shared.dto.SlackSendResponseDto;
import com.sixlab.logistics.common.shared.dto.SlackUserResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ResponseMessageListDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.SlackMessageInfoDto;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.MessageType;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.Slack;
import com.sixlab.logistics.slack_ai_service.Messenger.domain.repository.SlackRepository;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.SlackApiClient;
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

//    @Retry(name = "findEmailRetry", fallbackMethod = "fallbackGetSlackIdByEmail")
    public String getSlackIdByEmail(String email) {
        SlackUserResponseDto response = SlackClient.getUserByEmail(email);
        if(response.ok() && response.user() != null){
            return response.user().id();
        }
        return response.error(); //user not found
    }

    @Transactional
    public void sendSlackMessage(String email, SlackMessageInfoDto messageInfo) {
        String prompt = sendPromptMessage(messageInfo);

        String slackId = getSlackIdByEmail(email);

        SlackSendRequestDto requestDto = new SlackSendRequestDto(slackId,prompt);

        SlackSendResponseDto response = SlackClient.sendMessage(requestDto);

        if(!response.ok()){
            log.error("실패 메세지 " + response.error());
        }

        Slack slack = new Slack(
                response.channel(),
                prompt,
                response.ok() ? MessageType.SENT : MessageType.FAILED
        );

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
//    @Transactional(readOnly = true)
//    public List<ResponseMessageListDto> getUserBySlackMessage(String slackEmail) {
//
//    }


    @Transactional
    public void deletedMessage(UUID id) {
        Slack slack = slackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""+id));
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
