package com.sixlab.logistics.hub_service.hub.presentation.controller;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubCreateResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubUpdateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerResponseDto;
import com.sixlab.logistics.hub_service.hub.application.service.HubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs")
public class HubController {

    private final HubService hubService;

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping
    public ApiResponse<HubResponseDto> createHub(@Valid @RequestBody HubCreateRequestDto requestDto) {
        HubResponseDto response = hubService.createHub(requestDto);
        return ApiResponse.success(response, "허브가 생성되었습니다.");
    }

    @GetMapping("/{id}")
    public ApiResponse<HubResponseDto> getHub(@PathVariable UUID id) {
        HubResponseDto response = hubService.getHubById(id);
        return ApiResponse.success(response, "허브를 조회하였습니다.");
    }

    @PutMapping("/{id}")
    public ApiResponse<HubResponseDto> updateHub(
            @PathVariable UUID id,
            @Valid @RequestBody HubUpdateRequestDto requestDto) {

        HubResponseDto response = hubService.updateHub(id, requestDto);
        return ApiResponse.success(response, "허브가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteHub(@PathVariable UUID id) {
        hubService.deleteHub(id);
        return ApiResponse.success(null, "허브매니저가 삭제되었습니다.");
    }

    @GetMapping("/managers/{userId}")
    public ApiResponse<HubManagerResponseDto> getHubManagerByUserId(@PathVariable Long userId) {
        HubManagerResponseDto response = hubService.getManagerByUserId(userId);
        return ApiResponse.success(response, "허브매니저를 조회했습니다.");
    }




}
