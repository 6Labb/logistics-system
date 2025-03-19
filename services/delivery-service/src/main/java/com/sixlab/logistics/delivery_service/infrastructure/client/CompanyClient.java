package com.sixlab.logistics.delivery_service.infrastructure.client;

import com.sixlab.logistics.delivery_service.infrastructure.client.dto.CompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-service")
public interface CompanyClient {

    @GetMapping("/companies/{companyId}")
    CompanyResponseDto getCompanyId(@PathVariable UUID companyId);

}
