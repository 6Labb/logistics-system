package com.sixlab.logistics.product_service.application.client;

import com.sixlab.logistics.product_service.application.dto.CompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service")
public interface CompanyClient {
    @GetMapping("/api/companies/{companyId}")
    CompanyResponseDto getCompanyById(@PathVariable("companyId") String companyId);
}
