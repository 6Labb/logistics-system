package com.sixlab.logistics.company_service.presentation.controller;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.company_service.application.service.CompanyService;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 업체 등록
    @PostMapping
    public ApiResponse<CompanyResponseDto> createCompany(
            @RequestBody CompanyRequestDto requestDto){
        CompanyResponseDto responseDto = companyService.createCompany(requestDto);
        return ApiResponse.success(HttpStatus.CREATED, responseDto, "업체가 정상적으로 등록되었습니다.");
    }

    // 업체 목록 조회
    @GetMapping
    public ApiResponse<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> companies = companyService.getAllCompanies();
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

}
