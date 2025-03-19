package com.sixlab.logistics.common.shared.feign;

import com.sixlab.logistics.common.shared.dto.AiCreateRequestDto;
import com.sixlab.logistics.common.shared.dto.AiCreateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//uri?key=GEMINI_API_KEY
@FeignClient(name = "geminiApiClient", url = "${gemini.uri}")
public interface AiApiClient {

    @PostMapping
    AiCreateResponseDto callAi(@RequestBody AiCreateRequestDto request);
}
