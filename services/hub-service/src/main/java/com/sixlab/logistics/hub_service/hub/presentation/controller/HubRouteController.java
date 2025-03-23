package com.sixlab.logistics.hub_service.hub.presentation.controller;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteResponseDto;
import com.sixlab.logistics.hub_service.hub.application.service.HubManagerService;
import com.sixlab.logistics.hub_service.hub.application.service.HubRouteService;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs/routes")
public class HubRouteController {

    private final HubRouteService hubRouteService;

    @PostMapping
    public ApiResponse<HubRouteResponseDto> createHubRoute(@RequestBody HubRouteCreateRequestDto requestDto) {
        HubRouteResponseDto responseDto = hubRouteService.createHubRoute(requestDto);
        return ApiResponse.success(responseDto, "허브 이동 경로가 정상적으로 등록되었습니다.");
    }

    @GetMapping
    public ApiResponse<HubRouteResponseDto> getHubRoutes(@RequestParam UUID departureHubId, @RequestParam UUID arrivalHubId) {
        HubRouteResponseDto response = hubRouteService.getHubRoutes(departureHubId, arrivalHubId);
        return ApiResponse.success(response, "허브 이동 경로가 정상적으로 조회되었습니다.");
    }

//    @GetMapping("/{hubRouteId}")
//    public ResponseEntity<HubRouteResponseDto> getHubRoute(
//            @RequestParam UUID fromHubId,
//            @RequestParam UUID toHubId) {
//        HubRouteResponseDto response = hubRouteService.getHubRoute(fromHubId, toHubId);
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<HubRouteResponseDto> updateHubRoute(
//            @PathVariable UUID id,
//            @RequestBody HubRouteRequestDto requestDto) {
//        HubRouteResponseDto response = hubRouteService.updateHubRoute(id, requestDto);
//        return ResponseEntity.ok(response);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHubRoute(@PathVariable UUID id) {
        hubRouteService.deleteHubRoute(id);
        return ResponseEntity.ok().build();
    }



}
