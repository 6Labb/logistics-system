package com.sixlab.logistics.hub_service.hub.application.service;


import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteResponseDto;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRepository;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubRouteService {


    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

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
}
