package com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.SlackSendRequestDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.SlackSendResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.SlackUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//토큰값 Header
@FeignClient(name = "slackApiClient", url = "${slack.uri}")
public interface SlackApiClient {

    @GetMapping("/users.lookupByEmail")
    SlackUserResponseDto getUserByEmail(@RequestParam("email") String email,
                                        @RequestHeader("Authorization") String token);
    @PostMapping("/chat.postMessage")
    SlackSendResponseDto sendMessage(@RequestBody SlackSendRequestDto requestDto,
                                         @RequestHeader("Authorization") String token);
}
