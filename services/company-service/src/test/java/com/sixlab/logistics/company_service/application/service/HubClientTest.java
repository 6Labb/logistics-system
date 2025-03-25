package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.company_service.application.client.HubClient;
import com.sixlab.logistics.company_service.application.dto.ExternalCompanyResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HubClientTest {

    @Mock  // FeignClient를 Mocking하여 가짜 응답 설정
    private HubClient hubClient;

    @InjectMocks  // 테스트하려는 서비스에 Mock 객체를 주입
    private CompanyServiceImpl  companyServiceImpl;  // 회사 서비스가 HubClient를 호출한다고 가정

    @Test
    public void testFeignClientFallback() {
        // 가짜 Hub ID 생성
        UUID testHubId = UUID.randomUUID();

        // 허브 서비스가 정상적으로 응답할 경우를 가정한 Mock 설정
        ExternalCompanyResponse mockResponse = ExternalCompanyResponse.builder()
                .id(UUID.randomUUID())
                .name("Test Hub")
                .address("1234 Test Address")
                .hubId(UUID.randomUUID())
                .build();
        when(hubClient.getHubById(testHubId)).thenReturn(mockResponse);

        // 실제 서비스에서 Hub ID를 요청
        ExternalCompanyResponse response = hubClient.getHubById(testHubId);

        // 응답값이 Mock된 값과 일치하는지 검증
        assertEquals("Test Hub", response.getName());
        assertEquals("1234 Test Address", response.getAddress());

        //⃣ `hubClient.getHubById()`가 한 번 호출되었는지 검증
        verify(hubClient, times(1)).getHubById(testHubId);
    }

    @Test
    public void testFeignClientFallbackOnError() {
        // 가짜 Hub ID 생성
        UUID testHubId = UUID.randomUUID();

        // 허브 서비스가 오류를 반환하도록 설정
        when(hubClient.getHubById(testHubId)).thenThrow(new RuntimeException("허브 서비스 장애 발생"));

        try {
            // 실제 서비스에서 Hub ID를 요청 (예외 발생 예상)
            hubClient.getHubById(testHubId);
        } catch (Exception e) {
            // 예외가 발생했는지 확인
            assertEquals("허브 서비스 장애 발생", e.getMessage());
        }

        // `hubClient.getHubById()`가 한 번 호출되었는지 검증
        verify(hubClient, times(1)).getHubById(testHubId);
    }
}