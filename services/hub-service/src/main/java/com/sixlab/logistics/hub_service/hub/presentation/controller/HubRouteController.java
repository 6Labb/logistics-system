package com.sixlab.logistics.hub_service.hub.presentation.controller;


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
    public ResponseEntity<HubRouteResponseDto> createHubRoute(@RequestBody HubRouteRequestDto requestDto) {
        HubRouteResponseDto responseDto = hubRouteService.createHubRoute(requestDto);
        return ResponseEntity.ok(responseDto);
    }




}
