package com.sixlab.logistics.common.shared.feign;

import com.sixlab.logistics.common.shared.dto.SlackSendRequestDto;
import com.sixlab.logistics.common.shared.dto.SlackSendResponseDto;
import com.sixlab.logistics.common.shared.dto.SlackUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//토큰값 Header
@FeignClient(name = "slackApiClient", url = "${slack.uri}")
public interface SlackApiClient {

    @GetMapping("/users.lookupByEmail")
    SlackUserResponseDto getUserByEmail(@RequestParam("email") String email);
    @PostMapping("/chat.postMessage")
    SlackSendResponseDto sendMessage(@RequestBody SlackSendRequestDto requestDto);
}
