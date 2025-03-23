package com.sixlab.logistics.company_service.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.company_service.application.service.CompanyService;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import com.sixlab.logistics.company_service.presentation.dto.PaginationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 업체 등록
    //@PreAuthorize("hasRole('MASTER') or #id == #userDetails.user.id")
    @PostMapping
    public ApiResponse<CompanyResponseDto> createCompany(
            @RequestBody CompanyRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        CompanyResponseDto responseDto = companyService.createCompany(requestDto);
        return ApiResponse.success(HttpStatus.CREATED, responseDto, "업체가 정상적으로 등록되었습니다.");
    }

    // 업체 목록 조회
    @GetMapping
    public ApiResponse<PaginationResponseDto<CompanyResponseDto>> getAllCompanies(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationResponseDto<CompanyResponseDto> companies = companyService.getAllCompanies(page, size);
        return ApiResponse.success(companies, "업체 목록 조회 성공");
    }

    // 업체 단건 조회
    @GetMapping("/{companyId}")
    public ApiResponse<CompanyResponseDto> getCompanyById(
            @PathVariable UUID companyId) {
        CompanyResponseDto company = companyService.getCompanyById(companyId);
        return ApiResponse.success(company, "업체 조회 성공");
    }

    // 업체 수정
    @PutMapping("/{companyId}")
    public ApiResponse<CompanyResponseDto> updateCompany(
            @PathVariable UUID companyId,
            @RequestBody CompanyRequestDto requestDto) {
        CompanyResponseDto updatedCompany = companyService.updateCompany(companyId, requestDto);
        return ApiResponse.success(updatedCompany, "업체 수정 성공");
    }

    // 업체 삭제
    @DeleteMapping("/{companyId}")
    public ApiResponse<Void> deleteCompany (
            @PathVariable UUID companyId){
        companyService.deleteCompany(companyId);
        return ApiResponse.success(HttpStatus.OK, null, "업체가 정상적으로 삭제되었습니다.");
    }

    @GetMapping("/search")
    public ApiResponse<PaginationResponseDto<CompanyResponseDto>> searchCompanies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) UUID hubId,
            @PageableDefault Pageable pageable) {

        // 서비스에서 페이지네이션된 결과 가져오기
        Page<CompanyResponseDto> companies = companyService.searchCompanies(name, type, hubId, pageable);

        // 페이지네이션된 데이터를 PaginationResponseDto로 변환
        PaginationResponseDto<CompanyResponseDto> result = new PaginationResponseDto<>(
                companies.getContent(),
                companies.getPageable().getPageNumber(),
                companies.getPageable().getPageSize(),
                companies.getTotalElements(),
                companies.getTotalPages()
        );

        // ApiResponse로 감싸서 반환
        return ApiResponse.success(result, "업체 검색 성공");
    }

}
