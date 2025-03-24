package com.sixlab.logistics.slack_ai_service.Messenger.presentation.controller;

import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.OrderInfoMessageResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.application.service.ai.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
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

//    ai 응답호출
    @PostMapping("/call-test")
    public ResponseEntity<String> callAiTest(@RequestBody OrderInfoMessageResponseDto message) {
    aiService.processOrderAndNotifySlack(message, null, 0L);
    return ResponseEntity.ok("AI 처리 및 슬랙 전송 시도 완료");
}

    @PostMapping("/question")
    public ResponseEntity<String> geminiGetAnswer(@RequestParam String question) {
        String answer = aiService.generateContent(question);
        return ResponseEntity.ok(answer);
    }

}
