package com.sixlab.logistics.hub_service.hub.application.service;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.*;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubRepository;
import com.sixlab.logistics.hub_service.hub.infrastructure.feign.UserClient;
import com.sixlab.logistics.hub_service.hub.infrastructure.feign.ExternalUserResponseDto;
import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import com.sixlab.logistics.hub_service.hub.domain.repository.HubManagerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubManagerService {

    private final HubRepository hubRepository;

    private final HubManagerRepository hubManagerRepository;

    private final UserClient userClient;

    @Transactional
    public HubManagerCreateResponseDto createManager(HubManagerCreateRequestDto request) {

        System.out.println("FeignClient 호출: userId=" + request.getUserId());

        // SecurityContext에서 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("SecurityContext에서 사용자 정보를 찾을 수 없음");
        }

        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER");

        System.out.println("현재 로그인한 사용자 ID: " + currentUserId + ", 역할: " + currentUserRole);

        String token = authentication.getCredentials().toString();

        // FeignClient 호출 (JWT 자동 포함됨)
        //UserResponseDto userResponse = userClient.getUser(request.getUserId(), "Bearer " + token);
        ApiResponseDto<ExternalUserResponseDto> userResponse = userClient.getUser(request.getUserId());

        log.info("Feign 응답 전체: {}", userResponse);
        log.info("data: {}", userResponse.getData());
        log.info("slackId: {}", userResponse.getData().getSlackId());

        System.out.println("FeignClient 응답 수신: " + userResponse);

        if (userResponse == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID입니다: " + request.getUserId());
        }

        //ExternalUserResponseDto userData = userResponse.getData();

        Hub hub = hubRepository.findById(request.getHubId())
                .orElseThrow(() -> new IllegalArgumentException("해당 허브가 존재하지 않습니다."));

        // HubManager 생성
        HubManager hubManager = HubManager.create(request.getUserId(), userResponse.getData().getSlackId(), request.getHubId());

        hubManagerRepository.save(hubManager);

        hub.updateManagerUserId(request.getUserId());

        return HubManagerCreateResponseDto.of(hubManager);
    }


    @Transactional(readOnly = true)
    public HubManagerResponseDto getManagerById(UUID id) {
        HubManager hubManager = hubManagerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매니저 ID 입니다."));

        return HubManagerResponseDto.of(hubManager);
    }

    @Transactional(readOnly = true)
    public HubManagerResponseDto getManagerByUserId(Long id) {
        HubManager hubManager = hubManagerRepository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매니저 ID 입니다."));

        return HubManagerResponseDto.of(hubManager);
    }

    @Transactional
    public HubManagerResponseDto updateManager(UUID id, @Valid HubManagerUpdateRequestDto request) {
        HubManager hubManager = hubManagerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매니저 ID 입니다."));

        // 업데이트
        hubManager.update(request.getHubId());

        return HubManagerResponseDto.of(hubManager);
    }

    @Transactional
    public void deleteManager(UUID id) {
        HubManager hubManager = hubManagerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매니저 ID 입니다."));

        hubManager.delete(3L); // BasicEntity의 softDelete() 호출
    }

}
