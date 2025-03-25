package com.sixlab.logistics.delivery_service.delivery.infrastructure.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubService {
    private final HubClient hubClient;

    public HubRouteResponseDto getHubRouteId(UUID fromHubId, UUID toHubId) {
        ResponseEntity<ApiResponseDto<HubRouteResponseDto>> response = hubClient.getHubRouteId(fromHubId, toHubId);
        return response.getBody().getData();
    }

    public HubManagerResponseDto getHubIdByUserId(Long userId) {
        ResponseEntity<ApiResponseDto<HubManagerResponseDto>> response = hubClient.getHubIdByUserId(userId);
        return response.getBody().getData();
    }

    /*
    @GetMapping
    public ApiResponse<HubRouteResponseDto> getHubRoutes(
        @RequestParam UUID departureHubId,
        @RequestParam UUID arrivalHubId) {
        HubRouteResponseDto response = hubRouteService.getHubRoutes(departureHubId, arrivalHubId);
        return ApiResponse.success(response, "허브 이동 경로가 정상적으로 조회되었습니다.");
    }
     */
}
