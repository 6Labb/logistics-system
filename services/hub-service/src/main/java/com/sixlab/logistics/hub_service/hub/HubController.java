package com.sixlab.logistics.hub_service.hub;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs")
public class HubController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("/test")
    public String getHub() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }

    private final HubService hubService;

    //@PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER')")
    @PostMapping
    public ApiResponse<HubCreateResponseDto> createHub(@Valid @RequestBody HubCreateRequestDto requestDto) {
        HubCreateResponseDto response = hubService.createHub(requestDto);
        return ApiResponse.success(response, "Hub created");
    }

    @GetMapping("/{id}")
    public ApiResponse<HubResponseDto> getHub(@PathVariable UUID id) {
        HubResponseDto response = hubService.getHubById(id);
        return ApiResponse.success(response, "Hub found");
    }

}
