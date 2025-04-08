package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="hub-service")
public interface HubClient {
    /**
     * 주어진 허브의 식별자에 해당하는 정보를 조회한다.
     *
     * @param id 조회할 허브의 고유 식별자
     * @return 조회된 허브 정보를 포함하는 응답 엔티티
     */
    @GetMapping("/hubs/{id}")
    ResponseEntity<ApiResponseDto<HubResponseDto>> getHubById(@PathVariable UUID id);
    // ResponseEntity<TempDto> getHubById(@PathVariable UUID id);
}