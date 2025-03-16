package com.sixlab.logistics.company_service.domain.model;

import com.sixlab.logistics.common.domain.BasicEntity;
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
@Table(name = "p_company")
public class Company extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    /*@ManyToOne
    @JoinColumn(name = "hub_id")
    private Hub hub;*/

    // 엔티티에서 DTO로 변환
    public CompanyResponseDto toResponseDto() {
        return new CompanyResponseDto(this.id, this.name, this.address, this.type);
    }

    // DTO를 엔티티로 변환하는 메소드
    public static Company toEntity(CompanyRequestDto requestDto) {
        return Company.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .type(requestDto.getType()) // DTO에서 Enum을 그대로 받음
                //.hub(hub) // Hub는 외부에서 받아와야 함
                .build();
    }

    public void updateFromDto(CompanyRequestDto dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.type = CompanyType.valueOf(String.valueOf(dto.getType()));
    }

}
