package com.sixlab.logistics.delivery_service.delivery.infrastructure.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Component
@FeignClient(name = "hub-service", configuration = FeignClientInterceptor.class)
public interface HubClient {

    // ApiResponse = ResponseEntity<ApiResponseDto>
    // 허브 이동관리 id 조회
    @GetMapping("/hubs/routes")
    ResponseEntity<ApiResponseDto<HubRouteResponseDto>> getHubRouteId(
            @RequestParam("departureHubId") UUID fromHubId,
            @RequestParam("arrivalHubId") UUID toHubId);

    // user_id로 소속허브 id 조회
    @GetMapping("/hubs/managers/{userId}")
    ResponseEntity<ApiResponseDto<HubManagerResponseDto>> getHubIdByUserId(@PathVariable Long userId);

    /*
    @GetMapping
    public ApiResponse<HubRouteResponseDto> getHubRoutes(
            @RequestParam UUID departureHubId,
            @RequestParam UUID arrivalHubId) {
        HubRouteResponseDto response = hubRouteService.getHubRoutes(departureHubId, arrivalHubId);
        return ApiResponse.success(response, "허브 이동 경로가 정상적으로 조회되었습니다.");
    }
    @GetMapping("/managers/{userId}")
    public ApiResponse<HubManagerResponseDto> getHubManagerByUserId(@PathVariable Long userId) {
        HubManagerResponseDto response = hubService.getManagerByUserId(userId);
        return ApiResponse.success(response, "허브매니저를 조회했습니다.");
    }
     */
}
