package com.sixlab.logistics.hub_service.hub.application.service;


import com.sixlab.logistics.common.shared.exception.DeletedDataAccessException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteUpdateRequestDto;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRepository;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

    // 생성 잘 됨
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {
        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId())
                .orElseThrow(() -> new ResourceNotFoundException("출발 허브 없음"));

        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId())
                .orElseThrow(() -> new ResourceNotFoundException("도착 허브 없음"));

        HubRoute route = HubRoute.create(
                departureHub,
                arrivalHub,
                requestDto.getDistance(),
                requestDto.getDuration()
        );

        HubRoute saved = hubRouteRepository.save(route);
        return HubRouteResponseDto.from(saved);
    }

    // 조회 잘 됨
    @Transactional(readOnly = true)
    public HubRouteResponseDto getHubRoutes(UUID departureHubId, UUID arrivalHubId) {
        Hub departureHub = hubRepository.findById(departureHubId)
                .orElseThrow(() -> new ResourceNotFoundException("출발 허브 없음"));

        Hub arrivalHub = hubRepository.findById(arrivalHubId)
                .orElseThrow(() -> new ResourceNotFoundException("도착 허브 없음"));

        HubRoute hubRoute = hubRouteRepository.findByDepartureHubAndArrivalHub(departureHub, arrivalHub)
                .orElseThrow(() -> new ResourceNotFoundException("해당 경로 없음"));

        return HubRouteResponseDto.from(hubRoute);
    }

    // 수정 잘 됨
    @Transactional
    public HubRouteResponseDto updateHubRoute(UUID id, HubRouteUpdateRequestDto requestDto) {
        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId())
                .orElseThrow(() -> new ResourceNotFoundException("출발 허브 없음"));

        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId())
                .orElseThrow(() -> new ResourceNotFoundException("도착 허브 없음"));

        HubRoute hubRoute = hubRouteRepository.findByDepartureHubAndArrivalHub(departureHub, arrivalHub)
                .orElseThrow(() -> new ResourceNotFoundException("해당 경로 없음"));

        hubRoute.update(departureHub, arrivalHub, requestDto.getDistance(), requestDto.getDuration());

        return HubRouteResponseDto.from(hubRoute);
    }

    // 삭제 잘 됨
    @Transactional
    public void deleteHubRoute(UUID id, Long userId) {
        HubRoute hubRoute = hubRouteRepository.findById(id)
                .filter(route -> route.getDeletedAt() == null)
                .orElseThrow(() -> new DeletedDataAccessException("이미 삭제된 허브 경로입니다."));

        hubRoute.delete(userId);
    }

}
