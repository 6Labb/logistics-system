package com.sixlab.logistics.slack_ai_service.Messenger.application.service.ai;

import com.rabbitmq.client.Channel;
import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.slack_ai_service.Messenger.application.service.cache.CacheService;
import com.sixlab.logistics.slack_ai_service.Messenger.application.service.slack.SlackService;
import com.sixlab.logistics.slack_ai_service.Messenger.exception.GeminiRetryException;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.*;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackMessageInfoDto;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.DeliveryClient;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.GeminiApiClient;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.HubClient;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static com.sixlab.logistics.common.shared.response.ApiResponseHelper.extractData;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService{

    private static final String workingHour="09:00~18:00";
    private static final String PROMPT_MESSAGE =
                    "추가 고려 사항: 발송지 기준으로 넉넉하게 고려. 배송 담당자의 근무 시간을 벗어나지 않도록 해야 함. " +
                    "경유지에서 평균 처리 시간이 걸릴 수 있음 " +
                    "답변 형식: 위 내용을 기반으로 도출된 최종 발송 시한은 XX월 XX일 오전/오후 X시 입니다. 추가적인 설명 없이 이 형식으로만 답변해줘.";

    private final SlackService slackService;
    private final CacheService cacheService;
    private final GeminiApiClient aiClient;
    private final DeliveryClient deliveryClient;
    private final UserClient userClient;
    private final HubClient hubClient;

    @RabbitListener(queues = "orderInfo-queue")
    public void processOrderAndNotifySlack(OrderInfoMessageResponseDto queue,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long tag) {

        //발송허브담당자 - 출발허브-목적허브 배송 담당자?? 허브관리자면 현재 누가 해당 주문의 배송담당자인지 알수있는방법이 없음 허브 배송담당자로 진행
        ApiResponse<DeliveryClientResponseDto> deliveryResponse = deliveryClient.getDelivery(queue.getDeliveryId());
        DeliveryClientResponseDto deliveryData = extractData(deliveryResponse, Function.identity());

        ApiResponse<UserClientResponseDto> userResponse = userClient.getUser2(deliveryData.getHudDeliveryAgentId());
        UserClientResponseDto userData = extractData(userResponse, Function.identity());
        String userName = userData.getUserName();
        String slackId= userData.getSlackId();

        String fromHubName = cacheService.getHubName(deliveryData.getFromHubId());
        String toHubName = cacheService.getHubName(deliveryData.getToHubId());

        try {
            log.info("메시지 수신: " + queue);
            OrderInfoRequestDto infoDto = OrderInfoRequestDto.builder()
                    .productName(queue.getProductName())
                    .quantity(queue.getQuantity())
                    .startLocation(fromHubName)
                    .stopLocations(toHubName)
                    .destination(queue.getDestination())
                    .requestMessage(queue.getRequestMessage())
                    .workingHour(workingHour)
                    .build();

            String prompt = buildPromptText(infoDto);

            String aiDeadline = getAiResponse(prompt);
            log.info("AI 호출 확인"+aiDeadline);

            SlackMessageInfoDto sendSlackMessage = buildSlackMessage(queue,infoDto,userName,aiDeadline);

            slackService.sendSlackMessage(slackId,sendSlackMessage);

            channel.basicAck(tag, false); //큐 삭제
        } catch (IOException e) {
            log.error("메시지 처리 중 IO 오류 발생: {}", e.getMessage(), e);
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException nackEx) {
                log.error("basicNack 실패: {}", nackEx.getMessage(), nackEx);

            } catch (Exception ioException) {
                log.error("예상치 못한 오류 발생: {}", e.getMessage(), e);
            }
        }
    }

    @Retry(name = "geminiRetry")
    @CircuitBreaker(name = "gemini", fallbackMethod = "fallbackAiCall")
    public String getAiResponse(String prompt) {
        AiCreateRequestDto request = buildCallAiRequest(prompt);
        try {
            AiCreateResponseDto response = aiClient.callAi(request);
            return extractResultFromResponse(response);
        } catch (Exception e) {
            throw new GeminiRetryException("Gemini 호출 실패", e);
        }
    }

    public String generateContent(String text) {
        AiCreateResponseDto response = aiClient.callAi(buildCallAiRequest(text));
        return extractResultFromResponse(response);
    }

    //요청 변환
    private AiCreateRequestDto buildCallAiRequest(String text) {
        AiCreateRequestDto.Part part = new AiCreateRequestDto.Part(text);
        AiCreateRequestDto.Content content = new AiCreateRequestDto.Content(List.of(part));
        return new AiCreateRequestDto(List.of(content));
    }

    //응답 파싱
    private String extractResultFromResponse(AiCreateResponseDto response) {
        if (response.candidates() != null && !response.candidates().isEmpty()) {
            AiCreateResponseDto.Candidate candidate = response.candidates().get(0);
            if (candidate.content() != null && candidate.content().parts() != null && !candidate.content().parts().isEmpty()) {
                return candidate.content().parts().get(0).text();
            }
        }
        return "gemini 응답실패";
    }

    private String buildPromptText(OrderInfoRequestDto infoDto) {
        return String.format(
                "상품 정보: %s %d 요청 사항: %s " +
                        "발송지: %s 경유지: %s 도착지: %s 배송 담당자 근무시간: %s %s",
                infoDto.getProductName(),
                infoDto.getQuantity(),
                infoDto.getRequestMessage(),
                infoDto.getStartLocation(),
                infoDto.getStopLocations(),
                infoDto.getDestination(),
                infoDto.getWorkingHour(),
                PROMPT_MESSAGE
        );
    }

    private SlackMessageInfoDto buildSlackMessage(OrderInfoMessageResponseDto message, OrderInfoRequestDto infoDto,String userName ,String aiDeadline) {
        return SlackMessageInfoDto.builder()
                .orderId(message.getOrderId())
                .customerName(message.getReceiverName())
                .productName(message.getProductName())
                .quantity(message.getQuantity())
                .request(message.getRequestMessage())
                .sender(infoDto.getStartLocation())
                .transitCenters(infoDto.getStopLocations())
                .destination(message.getDestination())
                .deliveryManagerName(userName)
                .deadline(aiDeadline)
                .build();
    }

}
