package com.sixlab.logistics.hub_service.hub.presentation.controller;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerCreateResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerUpdateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.service.HubManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs/{id}/manager")
public class HubManagerController {

    private final HubManagerService hubManagerService;

    @PostMapping
    public ApiResponse<HubManagerCreateResponseDto> createHubManager(@Valid @RequestBody HubManagerCreateRequestDto request) {

        HubManagerCreateResponseDto response = hubManagerService.createManager(request);

        return ApiResponse.success(response, "HubManager created");
    }

    @GetMapping("/{id}")
    public ApiResponse<HubManagerResponseDto> getHubManager(@PathVariable UUID id) {
        HubManagerResponseDto response = hubManagerService.getManagerById(id);
        return ApiResponse.success(response, "HubManager retrieved");
    }


    @PutMapping("/{id}")
    public ApiResponse<HubManagerResponseDto> updateHubManager(
            @PathVariable UUID id,
            @Valid @RequestBody HubManagerUpdateRequestDto request) {

        HubManagerResponseDto response = hubManagerService.updateManager(id, request);
        return ApiResponse.success(response, "HubManager updated");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> softDeleteHubManager(@PathVariable UUID id) {
        hubManagerService.deleteManager(id);
        return ApiResponse.success(null, "HubManager soft deleted");
    }





}
