package com.sixlab.logistics.company_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class HubRouteResponse {
    private UUID departureHubId;
    private UUID arrivalHubId;
}
