package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.common.shared.dto.AiCreateRequestDto;
import com.sixlab.logistics.common.shared.dto.AiCreateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//uri?key=GEMINI_API_KEY
@FeignClient(name = "geminiApiClient", url = "${gemini.uri}")
public interface AiApiClient {

    @PostMapping
    AiCreateResponseDto callAi(@RequestBody AiCreateRequestDto request);
}
