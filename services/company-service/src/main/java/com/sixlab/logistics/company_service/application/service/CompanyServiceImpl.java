package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.common.shared.exception.DuplicateResourceException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.repository.CompanyRepository;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import com.sixlab.logistics.company_service.presentation.dto.ExternalCompanyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    @Transactional
    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto requestDto) {
        // hubId가 실제 Hub 서비스에 존재하는지 검증
//        ExternalCompanyResponse hubResponse = hubClient.getHubById(requestDto.getHubId());
//        if (hubResponse == null) {
//            throw new ResourceNotFoundException("해당 hubId가 존재하지 않습니다.");
//        }

        // 중복 검사
        if (companyRepository.existsByName(requestDto.getName())) {
            throw new DuplicateResourceException("이미 존재하는 회사입니다.");
        }

        // CompanyRequestDto를 Entity로 변환 -> hubId 포함되있음
        Company company = Company.toEntity(requestDto);

        // 업체 저장
        log.info("Saving company: {}", company);
        company = companyRepository.save(company);

        // 저장된 업체를 DTO로 변환하여 반환
        return new CompanyResponseDto(company);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        // 모든 업체 조회
        List<Company> companies = companyRepository.findAll();
        // 업체 리스트를 DTO로 변환하여 반환
        return companies.stream()
                .map(CompanyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CompanyResponseDto getCompanyById(UUID companyId) {
        // 업체 조회 (없는 경우 예외 처리)
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("업체를 찾을 수 없습니다."));

        // 조회된 업체를 DTO로 변환하여 반환
        return new CompanyResponseDto(company);
    }

    @Transactional
    @Override
    public CompanyResponseDto updateCompany(UUID companyId, CompanyRequestDto requestDto) {
        // 업체 조회 (없는 경우 예외 처리)
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("업체를 찾을 수 없습니다."));

        // 엔티티의 메소드 활용
        company.updateCompany(
                requestDto.getName(),
                requestDto.getAddress(),
                requestDto.getType(),
                requestDto.getHubId()
        );

        return new CompanyResponseDto(company);

    }

    @Transactional
    @Override
    public void deleteCompany(UUID companyId) {
        // 업체 조회 (없는 경우 예외 처리)
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("업체를 찾을 수 없습니다."));
        // 업체 삭제
        companyRepository.delete(company);
    }
}
