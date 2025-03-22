package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.rabbitmq.client.Channel;
import com.sixlab.logistics.common.shared.dto.AiCreateRequestDto;
import com.sixlab.logistics.common.shared.dto.AiCreateResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.*;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.GeminiApiClient;
import org.springframework.messaging.handler.annotation.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

    private final GeminiApiClient aiClient;

    @RabbitListener(queues = "orderInfo-queue")
    public void processOrderAndNotifySlack(OrderInfoMessageResponseDto message,
                                           Channel channel,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long tag) {

        /*
            rabbitMQ message 값
            private UUID orderId;
            private String productName;
            private Integer quantity;
            private String receiverName;
            private String destination;
            private String requestMessage;
            private UUID deliveryId;
        */

        //deliveryClient.getDeliveryInfo(); //담당자이름 출발지 경유지 조회후 slack service 전달
        //userClient.getUserInfo(); // 어차피 메시지를 보내는사람은 로그인한 주문자 본인 (권한또한 모든 사용자가 보냄)

        try {
            log.info("메시지 수신: " + message);
            OrderInfoRequestDto infoDto = OrderInfoRequestDto.builder()
                    .productName(message.getProductName())
                    .quantity(message.getQuantity())
                    .startLocation("서울역")
                    .stopLocations(List.of("대전역", "부산역"))
                    .destination(message.getDestination())
                    .requestMessage(message.getRequestMessage())
                    .workingHour(workingHour)
                    .build();
            
            String prompt = buildPromptText(infoDto);
            
            AiCreateRequestDto aiCreateRequestDto = buildCallAiRequest(prompt);
            
            String aiDeadline =extractResultFromResponse(aiClient.callAi(aiCreateRequestDto));
            log.info("AI 호출 확인"+aiDeadline);

            SlackMessageInfoDto sendSlackMessage = buildSlackMessage(message,infoDto,aiDeadline);

            slackService.sendSlackMessage("hu185@naver.com",sendSlackMessage); //이메일 없으면 오류남 ㅠㅠㅠ 그냥 일단 내꺼로 하드코딩
            
            channel.basicAck(tag, false); //큐 삭제 (수동)
        } catch (IOException e) {
            System.out.println("처리실패");
            try {
                channel.basicNack(tag, false, false);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
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

    private SlackMessageInfoDto buildSlackMessage(OrderInfoMessageResponseDto message, OrderInfoRequestDto infoDto, String aiDeadline) {
        return SlackMessageInfoDto.builder()
                .orderId(message.getOrderId())
                .customerName(message.getReceiverName())
                .productName(message.getProductName())
                .quantity(message.getQuantity())
                .request(message.getRequestMessage())
                .sender(infoDto.getStartLocation())
                .transitCenters(infoDto.getStopLocations())
                .destination(message.getDestination())
                .deliveryManagerName("고길동")
                .deadline(aiDeadline)
                .build();
    }

}
