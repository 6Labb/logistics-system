package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.common.shared.exception.DuplicateResourceException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.company_service.application.client.HubClient;
import com.sixlab.logistics.company_service.application.dto.HubResponse;
import com.sixlab.logistics.company_service.application.dto.HubRouteResponse;
import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.repository.CompanyRepository;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import com.sixlab.logistics.company_service.presentation.dto.PaginationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PaginationResponseDto<CompanyResponseDto> getAllCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companyPage = companyRepository.findAll(pageable);

        // 모든 업체 조회 -> 업체 리스트를 DTO로 변환하여 반환
        List<CompanyResponseDto> companyDtos = companyPage.getContent().stream()
                .map(CompanyResponseDto::new)
                .collect(Collectors.toList());
        //
        return PaginationResponseDto.<CompanyResponseDto>builder()
                .content(companyDtos)
                .page(companyPage.getNumber())
                .size(companyPage.getSize())
                .totalItems(companyPage.getTotalElements())
                .totalPages(companyPage.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CompanyResponseDto> searchCompanies(String name, String type, UUID hubId, Pageable pageable) {
        Page<Company> companies = companyRepository.searchCompanies(name, type, hubId, pageable);
        return companies.map(CompanyResponseDto::new);
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

    @Transactional(readOnly = true)
    @Override
    public HubRouteResponse getHubRoute(UUID supplierId, UUID receiverId) {
        log.info("Fetching hub info for supplierId: {}, receiverId: {}", supplierId, receiverId);

        // Company ID로 Hub ID 조회
        UUID departureHubId = companyRepository.findHubIdByCompanyId(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("출발 허브 정보를 찾을 수 없습니다."));
        UUID arrivalHubId = companyRepository.findHubIdByCompanyId(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("도착 허브 정보를 찾을 수 없습니다."));

        // 조회한 Hub ID로 Hub 정보 가져오기
        HubResponse departureHub = hubClient.getHubById(departureHubId);
        HubResponse arrivalHub = hubClient.getHubById(arrivalHubId);

        return new HubRouteResponse(departureHub.getHubId(), arrivalHub.getHubId());
    }
}
