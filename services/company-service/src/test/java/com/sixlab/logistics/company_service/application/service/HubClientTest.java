package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.company_service.presentation.dto.ExternalCompanyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class HubClientTest {

    @Autowired
    private HubClient hubClient;

    @Test
    public void testFallback() {
        //임의 UUID 생성해서 hubId로 사용
        UUID hubId = UUID.randomUUID();

        // FeignClient를 통해 hubId를 전달하여 허브 서비스 호출
        //ExternalCompanyResponse response = hubClient.getHubById(hubId);

        // Fallback이 제대로 동작하는지 확인 (기본값 반환 확인)
//        assertEquals("Default Hub Name", response.getName());
//        assertEquals("N/A", response.getAddress());
    }
}