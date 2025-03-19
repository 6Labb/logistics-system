package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.SlackMessageInfoDto;
import com.sixlab.logistics.common.shared.dto.SlackSendRequestDto;
import com.sixlab.logistics.common.shared.dto.SlackSendResponseDto;
import com.sixlab.logistics.common.shared.dto.SlackUserResponseDto;
import com.sixlab.logistics.common.shared.feign.SlackApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {
    @Value("${slack.token}")
    private String token;
    private final SlackApiClient SlackClient;

    //Retry 테스트중 yml 설정 해야함
    //@Retry(name = "findEmailRetry", fallbackMethod = "fallbackGetSlackIdByEmail")
    //@CircuitBreaker(name = "findEmailFailed" ,fallbackMethod = "fallbackGetSlackIdByEmail")
    public String getSlackIdByEmail(String email) {
        SlackUserResponseDto response = SlackClient.getUserByEmail(email);
        if(response.ok() && response.user() != null){
            return response.user().id();
        }
        return response.error(); //user not found
    }

    public void sendSlackMessage(String email, SlackMessageInfoDto messageInfo) {
        String text = String.format(
                "*🚛 배송 알림!*\n\n" +
                        "주문 번호: *%s*\n" +
                        "주문자 정보: %s\n" +
                        "상품 정보: %s %d박스\n" +
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
        String slackId = getSlackIdByEmail(email);
        log.info("slackEmail : " + email);
        SlackSendRequestDto requestDto = new SlackSendRequestDto(slackId,text);
        log.info("requestDto : " + requestDto);
        SlackSendResponseDto response = SlackClient.sendMessage(requestDto);
        log.info("response: " + response);
        if(!response.ok()){
            log.error("실패 메세지 " + response.error());
        }
    }


}
