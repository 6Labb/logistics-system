package com.sixlab.logistics.slack_ai_service.Messenger.presentation.controller;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.slack.ResponseMessageListDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RefreshScope
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/slack")
public class SlackController {
    private final SlackService slackService;

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("/port")
    public String getSlack() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }

    @GetMapping("/getId")
    public ResponseEntity<String> getSlackIdByEmail(@RequestParam("email") String email) {
        log.info("getSlackIdByEmail email : " + email);
        String slackId = slackService.getSlackIdByEmail(email);
        log.info("getSlackIdByEmail slackId : " + slackId);
        return ResponseEntity.ok(slackId);
    }

    @GetMapping("/allMessage")
    public ResponseEntity<Page> getOrderList(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc,
                                             @RequestParam(value = "search", required = false) String keyword) {
        //String username = userDetails.getUsername();
        Page<ResponseMessageListDto> slackHistory = slackService.getSlackMessage(keyword, page, size, isAsc);
        return ResponseEntity.ok(slackHistory);
    }

    @DeleteMapping("")
    public void deleteSlackMessage(@RequestParam("id") UUID id) {
        slackService.deletedMessage(id);
    }


    //이제 필요없음..
//    @PostMapping("/send")
//    public SlackSendResponseDto sendMessage(@RequestBody SlackSendRequestDto request){
//        return slackService.sendSlackMessage(request.channel(), request.text());
//    }
}

