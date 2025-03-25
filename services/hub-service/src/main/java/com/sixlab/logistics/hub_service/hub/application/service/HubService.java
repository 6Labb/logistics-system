package com.sixlab.logistics.hub_service.hub.application.service;


import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.*;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerResponseDto;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubManagerRepository;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRepository;
import com.sixlab.logistics.hub_service.hub.domain.repository.query.HubQueryRepository;
import org.springframework.cache.annotation.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;
    private final HubManagerRepository hubManagerRepository;
    private final HubQueryRepository hubQueryRepository;

    @Transactional
    public HubResponseDto createHub(HubCreateRequestDto requestDto) {

        hubManagerRepository.findByUserId(requestDto.getHubManagerUserId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 id는 허브 매니저가 아닙니다."));

        Hub hub = HubMapper.toEntity(requestDto);

        Hub savedHub = hubRepository.save(hub);

        return HubMapper.toDto(savedHub);
    }


    @Transactional(readOnly = true)
    public HubResponseDto getHubById(UUID id) {
        Hub hub = hubRepository.findById(id)
                .filter(h -> h.getDeletedAt() == null)
                .orElseThrow(() -> new EntityNotFoundException("허브를 찾을 수 없습니다."));
        return HubResponseDto.of(hub);
    }

    @Transactional
    public HubResponseDto updateHub(UUID id, HubUpdateRequestDto requestDto) {
        Hub hub = hubRepository.findById(id)
                .filter(h -> h.getDeletedAt() == null)
                .orElseThrow(() -> new EntityNotFoundException("허브를 찾을 수 없습니다."));

        // 허브 정보 업데이트
        hub.update(
                requestDto.getHubName() != null ? requestDto.getHubName() : hub.getHubName(),
                requestDto.getHubAddress() != null ? requestDto.getHubAddress() : hub.getHubAddress(),
                requestDto.getLatitude() != null ? requestDto.getLatitude() : hub.getLatitude(),
                requestDto.getLongitude() != null ? requestDto.getLongitude() : hub.getLongitude(),
                requestDto.getHubManagerUserId() != null ? requestDto.getHubManagerUserId() : hub.getHubManagerUserId()
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

    @Transactional(readOnly = true)
    public HubManagerResponseDto getManagerByUserId(Long userId) {
        HubManager hubManager = hubManagerRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매니저 ID 입니다."));

        return HubManagerResponseDto.of(hubManager);
    }


    @Transactional(readOnly = true)
    public Page<HubResponseDto> searchHubs(String keyword, String sort, String order, int page, int size) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Hub> result = hubQueryRepository.searchHubs(keyword, sort, order, pageable);
        return result.map(HubMapper::toDto);
    }


}
