package com.sixlab.logistics.slack_ai_service.Messenger.presentation.controller;
import com.sixlab.logistics.slack_ai_service.Messenger.application.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {
    private final AiService aiService;

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("/port")
    public String getAi() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }

    //ai 응답호출
    @PostMapping("/callAi")
    public void getSlack_AiResponse() {
        aiService.callAiResponseDto();

    }



}
