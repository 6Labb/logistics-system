package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackSendRequestDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackSendResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.SlackUserResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.config.SlackApiConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//토큰값 Header
@FeignClient(name = "slackApiClient", url = "${slack.uri}", configuration = SlackApiConfig.class)
public interface SlackApiClient {

    @GetMapping("/users.lookupByEmail")
    SlackUserResponseDto getUserByEmail(@RequestParam("email") String email);
    @PostMapping("/chat.postMessage")
    SlackSendResponseDto sendMessage(@RequestBody SlackSendRequestDto requestDto);
}
