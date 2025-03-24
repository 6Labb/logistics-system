package com.sixlab.logistics.delivery_service.delivery.infrastructure.client;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyClient companyClient;

    public CompanyResponseDto getCompanyId(UUID supplierId, UUID receiverId) {
        ApiResponse<CompanyResponseDto> response = companyClient.getCompanyId(supplierId, receiverId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    /*
    @GetMapping("/hub-route")
    public HubRouteResponse getHubRoute(
            @RequestParam UUID supplierId,
            @RequestParam UUID receiverId) {
        return companyService.getHubRoute(supplierId, receiverId);
    }
    */

}
