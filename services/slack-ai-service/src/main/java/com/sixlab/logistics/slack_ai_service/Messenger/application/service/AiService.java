package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.*;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.AiApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {
    @Value("${gemini.key}")
    private String key;
    private static final String workingHour="09:00~18:00";
    public static final String PROMPT_MESSAGE =
            "추가 고려 사항: 발송지 기준으로 넉넉하게 고려. 배송 담당자의 근무 시간을 벗어나지 않도록 해야 함. " +
                    "경유지에서 평균 처리 시간이 걸릴 수 있음 " +
                    "답변 형식: 위 내용을 기반으로 도출된 최종 발송 시한은 XX월 XX일 오전/오후 X시 입니다. 추가적인 설명 없이 이 형식으로만 답변해줘.";

    private final SlackService slackService;

    private final AiApiClient aiClient;

    public void callAiResponseDto() {
        //할거
        // 이벤트트리거 컨슈머처리 프로듀서 설정(받아올 데이터 주문? 배송?)
        // feignClient 받아서 싹다 조회후 넘겨줌 (캐싱이나 세션으로 받아올 곳 찾아보기)
        // 예외처리랑 재시도 로직 하고 나중에 리팩토링 ㄱㄱ
        OrderInfoDto infoDto=new OrderInfoDto("마른오징어",50,"서울역",
                List.of("대전역","부산역"),"부산시 사하구 낙동대로 1번길 1 해산물월드","12월 12일 3시까지 도착해야 함",workingHour);

        String text = String.format(
                "상품 정보: %s %d 요청 사항: %s " +
                "발송지: %s 경유지: %s 도착지: %s 배송 담당자 근무시간: %s "+ PROMPT_MESSAGE,
                infoDto.getProductName(),
                infoDto.getQuantity(),
                infoDto.getAdditionalInfo(),
                infoDto.getSender(),
                infoDto.getWaypoints(),
                infoDto.getDestination(),
                infoDto.getWorkHours());
        log.info("요청데이터 확인 "+text);

        //요청 변환
        AiCallRequestDto aiCallRequestDto = new AiCallRequestDto(List.of(
                new AiCallRequestDto.Content(List.of(
                        new AiCallRequestDto.Part(text)
                ))
        ));
        //응답 파싱
        String response =extractResultFromResponse(aiClient.callAi(key, aiCallRequestDto));
        log.info("응답값 확인"+response);

        //Slack 보내줄 값
        SlackMessageInfoDto dto = SlackMessageInfoDto.builder()
                .orderId("1")
                .customerName("김말숙")
                .productName(infoDto.getProductName())
                .quantity(infoDto.getQuantity())
                .request(infoDto.getAdditionalInfo())
                .sender(infoDto.getSender())
                .transitCenters(infoDto.getWaypoints())
                .destination(infoDto.getDestination())
                .deliveryManagerName("고길동")
                .deadline(response)
                .build();



        slackService.sendSlackMessage("hu185@naver.com",dto);
    }

    //요청 변환(나중에 다른쪽에서 ai 필요하면 쓰면댐)
    private AiCallRequestDto buildCallAiRequest(TestRequestDto testRequest) {
        AiCallRequestDto.Part part = new AiCallRequestDto.Part(testRequest.getMessage());
        AiCallRequestDto.Content content = new AiCallRequestDto.Content(List.of(part));
        return new AiCallRequestDto(List.of(content));
    }
    
    //응답 파싱
    private String extractResultFromResponse(AiCallResponseDto response) {
        if (response.candidates() != null && !response.candidates().isEmpty()) {
            AiCallResponseDto.Candidate candidate = response.candidates().get(0);
            if (candidate.content() != null && candidate.content().parts() != null && !candidate.content().parts().isEmpty()) {
                return candidate.content().parts().get(0).text();
            }
        }
        return "gemini 응답실패";
    }

}
