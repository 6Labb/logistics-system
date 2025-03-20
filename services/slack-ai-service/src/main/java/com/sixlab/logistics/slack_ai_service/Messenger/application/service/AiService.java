package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.sixlab.logistics.common.shared.dto.AiCreateRequestDto;
import com.sixlab.logistics.common.shared.dto.AiCreateResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.*;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.AiApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final AiApiClient aiClient;

    //@KafkaListener(topics = "order-topic", groupId = "order-group")
    public void processOrderAndNotifySlack() {
        OrderInfoDto infoDto=new OrderInfoDto("마른오징어",50,"서울역",
                List.of("대전역","부산역"),"부산시 사하구 낙동대로 1번길 1 해산물월드","12월 12일 3시까지 도착해야 함",workingHour);

        String prompt = buildPromptText(infoDto);

        AiCreateRequestDto aiCreateRequestDto = buildCallAiRequest(prompt);

        String aiDeadline =extractResultFromResponse(aiClient.callAi(aiCreateRequestDto));
        log.info("응답값 확인"+aiDeadline);

        SlackMessageInfoDto sendSlackMessage = buildSlackMessage(infoDto,aiDeadline);


        slackService.sendSlackMessage("hu185@naver.com",sendSlackMessage);
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

    private String buildPromptText(OrderInfoDto infoDto) {
        return String.format(
                "상품 정보: %s %d 요청 사항: %s " +
                        "발송지: %s 경유지: %s 도착지: %s 배송 담당자 근무시간: %s %s",
                infoDto.getProductName(),
                infoDto.getQuantity(),
                infoDto.getAdditionalInfo(),
                infoDto.getSender(),
                infoDto.getWaypoints(),
                infoDto.getDestination(),
                infoDto.getWorkHours(),
                PROMPT_MESSAGE
        );
    }

    private SlackMessageInfoDto buildSlackMessage(OrderInfoDto infoDto, String aiDeadline) {
        return SlackMessageInfoDto.builder()
                .orderId("1") // 실제 OrderInfoDto 내부 값으로 변경 고려
                .customerName("김말숙") // 실제 데이터 사용 가능
                .productName(infoDto.getProductName())
                .quantity(infoDto.getQuantity())
                .request(infoDto.getAdditionalInfo())
                .sender(infoDto.getSender())
                .transitCenters(infoDto.getWaypoints())
                .destination(infoDto.getDestination())
                .deliveryManagerName("고길동")
                .deadline(aiDeadline)
                .build();
    }

}
