package com.sixlab.logistics.hub_service.hub.application.service;


import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubCreateResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubUpdateRequestDto;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    public HubCreateResponseDto createHub(HubCreateRequestDto requestDto) {

        Hub hub = Hub.create(requestDto.getHubName(), requestDto.getHubAddress(), requestDto.getLatitude(), requestDto.getLongitude(), requestDto.getHubManagerId());

        hubRepository.save(hub);

        return HubCreateResponseDto.builder()
                .hubName(requestDto.getHubName())
                .address(requestDto.getHubAddress())
                .build();
    }

    public HubResponseDto getHubById(UUID id) {
        Hub hub = hubRepository.findById(id)
                .filter(h -> h.getDeletedAt() == null) // 삭제된 데이터 제외
                .orElseThrow(() -> new EntityNotFoundException("허브를 찾을 수 없습니다."));
        return HubResponseDto.of(hub);
    }

    @Transactional
    public HubResponseDto updateHub(UUID id, HubUpdateRequestDto requestDto) {
        Hub hub = hubRepository.findById(id)
                .filter(h -> h.getDeletedAt() == null) // 삭제된 데이터 제외
                .orElseThrow(() -> new EntityNotFoundException("허브를 찾을 수 없습니다."));

        // 허브 정보 업데이트
        hub.update(
                requestDto.getHubName() != null ? requestDto.getHubName() : hub.getHubName(),
                requestDto.getHubAddress() != null ? requestDto.getHubAddress() : hub.getHubAddress(),
                requestDto.getLatitude() != null ? requestDto.getLatitude() : hub.getLatitude(),
                requestDto.getLongitude() != null ? requestDto.getLongitude() : hub.getLongitude(),
                requestDto.getHubManagerId() != null ? requestDto.getHubManagerId() : hub.getHubManagerId()
        );

        return HubResponseDto.of(hub);
    }

    @Transactional
    public void deleteHub(UUID id) {
        Hub hub = hubRepository.findById(id)
                .filter(h -> h.getDeletedAt() == null) // 삭제된 데이터 제외
                .orElseThrow(() -> new EntityNotFoundException("허브를 찾을 수 없습니다."));

        hub.delete(3L); // BasicEntity의 softDelete() 메서드 호출
    }

}
