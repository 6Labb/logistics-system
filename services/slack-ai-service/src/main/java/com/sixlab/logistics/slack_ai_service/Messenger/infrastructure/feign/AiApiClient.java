package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.AiCallRequestDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.AiCallResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//uri?key=GEMINI_API_KEY
@FeignClient(name = "geminiApiClient", url = "${gemini.uri}")
public interface AiApiClient {

    @PostMapping
    AiCallResponseDto callAi(@RequestParam String key, @RequestBody AiCallRequestDto request);
}
