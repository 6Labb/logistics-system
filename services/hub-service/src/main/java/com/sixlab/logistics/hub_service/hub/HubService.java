package com.sixlab.logistics.hub_service.hub;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    private final UserClient userClient;

    public HubCreateResponseDto createHub(HubCreateRequestDto requestDto) {

        Hub hub = Hub.create(requestDto.getHubName(), requestDto.getHubAddress(), requestDto.getLatitude(), requestDto.getLongitude());

        hubRepository.save(hub);

        return HubCreateResponseDto.builder()
                .hubName(requestDto.getHubName())
                .build();
    }

//    public UserResponseDto getUserInfo(Long userId) {
//        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
//        return userClient.getUserById(userId);
//    }

    public HubResponseDto getHubById(UUID id) {
        Hub hub = hubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("허브를 찾을 수 없습니다."));
        return HubResponseDto.of(hub);
    }
}
