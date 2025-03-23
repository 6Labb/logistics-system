package com.sixlab.logistics.company_service.application.client;

import com.sixlab.logistics.company_service.application.dto.HubResponse;
import com.sixlab.logistics.company_service.application.dto.ExternalCompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {

    @GetMapping("/hubs/{hubId}")
    HubResponse getHubById(@PathVariable("hubId") UUID hubId);

}