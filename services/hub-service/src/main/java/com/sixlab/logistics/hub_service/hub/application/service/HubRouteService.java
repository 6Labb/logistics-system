package com.sixlab.logistics.hub_service.hub.application.service;


import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteCreateRequestDto;
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

    // 잘 됨
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {
        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId())
                .orElseThrow(() -> new IllegalArgumentException("출발 허브 없음"));

        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId())
                .orElseThrow(() -> new IllegalArgumentException("도착 허브 없음"));

        HubRoute route = HubRoute.create(
                departureHub,
                arrivalHub,
                requestDto.getDistance(),
                requestDto.getDuration()
        );

        HubRoute saved = hubRouteRepository.save(route);
        return HubRouteResponseDto.from(saved);
    }

    @Transactional(readOnly = true)
    public HubRouteResponseDto getHubRoutes(UUID departureHubId, UUID arrivalHubId) {
        Hub departureHub = hubRepository.findById(departureHubId)
                .orElseThrow(() -> new IllegalArgumentException("출발 허브 없음"));

        Hub arrivalHub = hubRepository.findById(arrivalHubId)
                .orElseThrow(() -> new IllegalArgumentException("도착 허브 없음"));

        HubRoute hubRoute = hubRouteRepository.findByDepartureHubAndArrivalHub(departureHub, arrivalHub)
                .orElseThrow(() -> new IllegalArgumentException("해당 경로 없음"));

        return HubRouteResponseDto.from(hubRoute);
    }

//    @Transactional
//    public HubRouteResponseDto updateHubRoute(UUID id, HubRouteRequestDto requestDto) {
//        HubRoute hubRoute = hubRouteRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 경로 ID"));
//
//        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId())
//                .orElseThrow(() -> new IllegalArgumentException("출발 허브 없음"));
//
//        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId())
//                .orElseThrow(() -> new IllegalArgumentException("도착 허브 없음"));
//
//        hubRoute.update(departureHub, arrivalHub, requestDto.getDistance(), requestDto.getDuration());
//
//        return HubRouteResponseDto.from(hubRoute);
//    }

    @Transactional
    public void deleteHubRoute(UUID id) {
        HubRoute hubRoute = hubRouteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 경로 ID"));
        hubRoute.delete(123L);
    }
}
