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

    // 엔티티에서 DTO로 변환
    public CompanyResponseDto toResponseDto() {
        return new CompanyResponseDto(this.id, this.name, this.address, this.type);
    }

    // DTO를 엔티티로 변환하는 메소드
    public static Company toEntity(CompanyRequestDto requestDto) {
        return Company.builder()
                .id(UUID.randomUUID())
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .type(requestDto.getType()) // DTO에서 Enum을 그대로 받음
                .hubId(requestDto.getHubId())  // Hub UUID는 DTO에서 받음
                .build();
    }

    // DTO로부터 엔티티를 업데이트하는 메소드
    public void updateFromDto(CompanyRequestDto dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();

        try {
            this.type = CompanyType.valueOf(dto.getType().name());  // enum 값으로 매핑
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid company type: " + dto.getType());
        }

        this.hubId = dto.getHubId();  // 허브 UUID도 업데이트
    }

}
