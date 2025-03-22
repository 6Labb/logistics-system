package com.sixlab.logistics.hub_service.hub.application.service;


import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteResponseDto;
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

    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteRequestDto requestDto) {
        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId())
                .orElseThrow(() -> new IllegalArgumentException("출발 허브 없음"));

        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId())
                .orElseThrow(() -> new IllegalArgumentException("도착 허브 없음"));

        HubRoute route = new HubRoute(
                departureHub,
                arrivalHub,
                requestDto.getDistance(),
                requestDto.getDuration()
        );

        HubRoute saved = hubRouteRepository.save(route);
        return HubRouteResponseDto.from(saved);
    }

    @Transactional(readOnly = true)
    public HubRouteResponseDto getHubRoute(UUID hubRouteId, UUID fromHubId, UUID toHubId) {

        HubRoute hubRoute = hubRouteRepository.findByIdAndDeletedAtIsNull(hubRouteId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 경로 ID입니다."));

        if (!hubRoute.getDepartureHub().getId().equals(fromHubId) ||
                !hubRoute.getArrivalHub().getId().equals(toHubId)) {
            throw new IllegalArgumentException("출발 허브 또는 도착 허브가 경로와 일치하지 않습니다.");
        }

        return HubRouteResponseDto.from(hubRoute);
    }

    @Transactional(readOnly = true)
    public HubRouteResponseDto getHubRoutes(UUID id) {
        HubRoute hubRoute = hubRouteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 경로 ID"));
        return HubRouteResponseDto.from(hubRoute);
    }

    @Transactional
    public HubRouteResponseDto updateHubRoute(UUID id, HubRouteRequestDto requestDto) {
        HubRoute hubRoute = hubRouteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 경로 ID"));
        hubRouteRepository.update(requestDto);
        return HubRouteResponseDto.from(hubRoute);
    }

    @Transactional
    public void deleteHubRoute(UUID id) {
        HubRoute hubRoute = hubRouteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 경로 ID"));
        hubRoute.delete(123L);
    }
}
