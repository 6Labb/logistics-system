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
@RequestMapping("/hubs/{hubId}/manager")
public class HubManagerController {

    private final HubManagerService hubManagerService;

    @PostMapping
    public ApiResponse<HubManagerCreateResponseDto> createHubManager(@Valid @RequestBody HubManagerCreateRequestDto request) {

        HubManagerCreateResponseDto response = hubManagerService.createManager(request);

        return ApiResponse.success(response, "HubManager created");
    }

    @GetMapping("/{managerId}")
    public ApiResponse<HubManagerResponseDto> getHubManager(@PathVariable UUID managerId) {
        HubManagerResponseDto response = hubManagerService.getManagerById(managerId);
        return ApiResponse.success(response, "HubManager retrieved");
    }


    @PutMapping("/{managerId}")
    public ApiResponse<HubManagerResponseDto> updateHubManager(
            @PathVariable UUID managerId,
            @Valid @RequestBody HubManagerUpdateRequestDto request) {

        HubManagerResponseDto response = hubManagerService.updateManager(managerId, request);
        return ApiResponse.success(response, "HubManager updated");
    }

    @DeleteMapping("/{managerId}")
    public ApiResponse<Void> softDeleteHubManager(@PathVariable UUID managerId) {
        hubManagerService.deleteManager(managerId);
        return ApiResponse.success(null, "HubManager soft deleted");
    }





}
