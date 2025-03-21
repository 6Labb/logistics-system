package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.GetCompanyResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="company-service")
public interface CompanyClient {
    @GetMapping("/companies/{companyId}")
    ResponseEntity<ApiResponseDto<GetCompanyResponseDto>> getCompanyById(@PathVariable UUID companyId);

}
