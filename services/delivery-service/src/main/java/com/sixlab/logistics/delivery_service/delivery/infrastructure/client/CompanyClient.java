package com.sixlab.logistics.delivery_service.delivery.infrastructure.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.CompanyResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.config.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "company-service", configuration = FeignClientInterceptor.class)
public interface CompanyClient {

    @GetMapping("/companies/hub-route")
    ResponseEntity<ApiResponseDto<CompanyResponseDto>> getCompanyId(
            @RequestParam("supplierId") UUID supplierId,
            @RequestParam("receiverId") UUID receiverId);

    /*
    @GetMapping("/hub-route")
    public HubRouteResponse getHubRoute(
            @RequestParam UUID supplierId,
            @RequestParam UUID receiverId) {
        return companyService.getHubRoute(supplierId, receiverId);
    }
    */

}
