package com.sixlab.logistics.hub_service.hub.presentation.controller;


import com.sixlab.logistics.hub_service.hub.application.service.HubManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs")
public class HubRouteController {

    private final HubManagerService hubManagerService;





}
