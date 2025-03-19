package com.sixlab.logistics.company_service.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.company_service.presentation.dto.CompanyRequestDto;
import com.sixlab.logistics.company_service.presentation.dto.CompanyResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_company", schema = "companies")
public class Company extends BasicEntity {
    @Id
    private UUID id;

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    private UUID hubId;

    // Company 엔티티 -> CompanyResponseDto로 변환
    public CompanyResponseDto toResponseDto() {
        return new CompanyResponseDto(this.id, this.name, this.address, this.type);
    }
    // CompanyRequestDto를 받아 Company 엔티티로 변환하는 메소드
    public static Company toEntity(CompanyRequestDto requestDto) {
        return Company.builder()
                .id(UUID.randomUUID())
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .type(requestDto.getType())
                .hubId(requestDto.getHubId())  // Hub UUID는 DTO에서 받음
                .build();
    }

    public void updateCompany(String name, String address, CompanyType type, UUID hubId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.hubId = hubId;
    }


}
