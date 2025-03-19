package com.sixlab.logistics.slack_ai_service.Messenger.application.service;

import com.sixlab.logistics.common.shared.dto.AiCreateRequestDto;
import com.sixlab.logistics.common.shared.dto.AiCreateResponseDto;
import com.sixlab.logistics.common.shared.feign.AiApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final AiApiClient aiClient;

    public String gemini(String text) {

        AiCreateResponseDto response = aiClient.callAi(buildCallAiRequest(text));
        return extractResultFromResponse(response);
    }


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
}
