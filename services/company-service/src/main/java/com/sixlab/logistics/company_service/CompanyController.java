package com.sixlab.logistics.company_service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 업체 등록
    @PostMapping
    public ResponseEntity<CompanyResponseDto> createCompany(
            @RequestBody CompanyRequestDto request){
        CompanyResponseDto responseDto = companyService.createCompany(request);
        return ResponseEntity.ok().body(responseDto);
    }

    // 업체 목록 조회
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> companies = companyService.getAllCompanies();
        return ResponseEntity.ok().body(companies);
    }
    @Value("${server.port}")
    private String serverPort;

    // 업체 단건 조회
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(
            @PathVariable UUID companyId) {
        CompanyResponseDto company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok().body(company);
    }
    @Value("${message}")
    private String message;

    // 업체 수정
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDto> updateCompany(
            @PathVariable UUID companyId){
        CompanyResponseDto updatedCompany = companyService.updateCompany(companyId);
        return ResponseEntity.ok().body(updatedCompany);
    @GetMapping("/companies")
    public String getCompany() {
        return "info!!! From port : " + serverPort + "and message : " + message;
    }

    // 업체 삭제
    @DeleteMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDto> deleteCompany(
            @PathVariable UUID companyId){
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }

}
