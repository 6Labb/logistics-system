package com.sixlab.logistics.company_service.config;

import com.sixlab.logistics.company_service.application.client.HubClient;
import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.model.CompanyType;
import com.sixlab.logistics.company_service.domain.repository.CompanyRepository;
import com.sixlab.logistics.company_service.presentation.dto.ExternalCompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class CompanyDummyDataLoader {
    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    @Bean
    public ApplicationRunner initCompanies() {
        return args -> {
            // 중복 방지를 위해 "회사 A" 데이터 존재 여부 확인
            if (companyRepository.existsByName("회사 A")) {
                System.out.println("=== 이미 회사 데이터가 존재합니다. 더미 데이터 삽입을 건너뜁니다. ===");
                return;
            }

            UUID hubId = getHubIdFromHubService(); // 허브 ID 가져오기

            List<Company> companies = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> Company.builder()
                            .id(UUID.randomUUID())
                            .name("회사 " + i)
                            .address("도시 " + i + "번지")
                            .type(i % 2 == 0 ? CompanyType.SUPPLIER : CompanyType.RECEIVER)
                            .hubId(hubId)
                            .build()
                    )
                    .toList();

            companyRepository.saveAll(companies);
            System.out.println("=== 회사 더미 데이터 10개 삽입 완료 ===");
        };
    }

    /**
     * 허브 서비스 API를 호출하여 Hub ID를 가져오는 메소드
     */
    private UUID getHubIdFromHubService() {
        try {
            //UUID hubId = hubClient.getHubById(); // FeignClient 호출
            // 테스트용 기본 허브 ID
            UUID testHubId = UUID.fromString("11111111-1111-1111-1111-111111111111");
            ExternalCompanyResponse response = hubClient.getHubById(testHubId);

            if (response != null && response.getHubId() != null) {
                System.out.println("=== 허브 서비스에서 가져온 Hub ID: " + response.getHubId() + " ===");
                return response.getHubId();
            }
        } catch (Exception e) {
            System.err.println("=== 허브 서비스 호출 실패, 랜덤 Hub ID 사용 ===");
        }

        return UUID.randomUUID(); // 실패 시 랜덤 UUID 사용
    }
}
