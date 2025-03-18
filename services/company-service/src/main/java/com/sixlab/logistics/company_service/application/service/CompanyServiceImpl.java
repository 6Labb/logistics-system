package com.sixlab.logistics.company_service.application.service;

import com.sixlab.logistics.common.shared.exception.DuplicateResourceException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.company_service.domain.model.Company;
import com.sixlab.logistics.company_service.domain.repository.CompanyRepository;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;
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
        // 중복 검사
        if (companyRepository.existsByName(requestDto.getName())) {
            throw new DuplicateResourceException("이미 존재하는 회사입니다.");
        }
        // CompanyRequestDto를 Entity로 변환
        Company company = Company.toEntity(requestDto);
        // 업체 저장
        log.info("Saving company: {}", company);
        company = companyRepository.save(company);
        // 저장된 업체를 DTO로 변환하여 반환 (필요한 경우 추가적인 변환 메소드 작성)
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

        // DTO의 값을 이용하여 업체 정보를 업데이트
        company.updateFromDto(requestDto); // 엔티티 내에서 DTO 값을 반영하도록 메소드 구현

        try {
            // 업체 저장
            company = companyRepository.save(company);
        } catch (OptimisticLockException e) {
            throw new ConcurrentModificationException("동시에 수정된 업체입니다. 다시 시도해주세요.");
        }
        // 수정된 업체를 DTO로 변환하여 반환
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
