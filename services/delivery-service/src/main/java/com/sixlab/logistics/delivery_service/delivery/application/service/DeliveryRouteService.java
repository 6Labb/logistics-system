package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRouteStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRouteRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.service.DeliveryAgentService;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "DeliveryRouteService")
public class DeliveryRouteService {

    private final HubService hubService;
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryAgentService deliveryAgentService;

    // 배송 경로 목록 조회
    public Page<DeliveryRouteResponseDto> getAllDeliveryRoute(DeliveryRouteSearchDto searchDto, Pageable pageable) {

        return deliveryRouteRepository.searchDeliveryRouteList(searchDto, pageable);
    }

    // 특정 배송 모든 경로 조회
    /*
    public Page<DeliveryRouteResponseDto> getAllDeliveryRouteByDeliveryId(DeliveryRouteSearchDto searchDto, Pageable pageable, UUID deliveryId) {

        return deliveryRouteRepository.searchDeliveryRouteList(searchDto, pageable);
    }
    */

    // 배송 개별 조회
    public DeliveryRouteResponseDto getDeliveryRouteByDeliveryId(UUID deliveryId, UUID id) {

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송 경로 수정
    @Transactional
    public DeliveryRouteResponseDto updateDeliveryRoute(UUID deliveryId, UUID id, DeliveryRouteRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryRoute deliveryRoute;

        // 관리자
        if(currentRole == Role.MASTER) {
            deliveryRoute = deliveryRouteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 경로 정보를 찾을 수 없습니다."));
        }
        // 허브담당자
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);

            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            deliveryRoute = deliveryRouteRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //deliveryRoute = deliveryRouteRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 경로의 수정 권한이 없습니다."));
        }
        // 배송담당자는 본인의 배송만 가능
        else if (currentRole == Role.DELIVERY_AGENT) {
            deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 경로의 수정 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 경로 수정 권한이 없습니다.");
        }

        deliveryRoute.updateDeliveryRoute(requestDto);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송 경로 상태 변경
    @Transactional
    public DeliveryRouteStatusResponseDto updateDeliveryRouteStatus(UUID deliveryId, UUID id, DeliveryRouteStatus status, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryRoute deliveryRoute;

        // 관리자
        if(currentRole == Role.MASTER) {
            deliveryRoute = deliveryRouteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 경로 정보를 찾을 수 없습니다."));
        }
        // 허브담당자
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            deliveryRoute = deliveryRouteRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //deliveryRoute = deliveryRouteRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 경로의 수정 권한이 없습니다."));
        }
        // 배송담당자
        else if (currentRole == Role.DELIVERY_AGENT) {
            deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 경로의 수정 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 경로 수정 권한이 없습니다.");
        }

        deliveryRoute.updateDeliveryRouteStatus(status);

        return new DeliveryRouteStatusResponseDto(deliveryRoute.getStatus());
    }

    // 배송 경로 삭제
    @Transactional
    public DeliveryRouteResponseDto deleteDeliveryRoute(UUID deliveryId, UUID id, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryRoute deliveryRoute;
        // 관리자
        if(currentRole == Role.MASTER) {
            deliveryRoute = deliveryRouteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 경로 정보를 찾을 수 없습니다."));
        }
        // 허브담당자
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            deliveryRoute = deliveryRouteRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //deliveryRoute = deliveryRouteRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 경로의 수정 권한이 없습니다."));
        }
        // 배송담당자
        else if (currentRole == Role.DELIVERY_AGENT) {
            deliveryRoute = deliveryRouteRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 경로의 수정 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 경로 수정 권한이 없습니다.");
        }

        // 삭제 전 유효성 검사 - 배송대기 상태일 때만 삭제 가능
        if (deliveryRoute.getStatus() != DeliveryRouteStatus.WAITING) {
            throw new IllegalStateException("배송대기 상태의 배송기록만 삭제할 수 있습니다.");
        }

        deliveryRoute.delete(currentUserId);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송경로 생성
    @Transactional
    public DeliveryRouteResponseDto createDeliveryRoute(UUID deliveryId) {
        // 배송 존재 여부 확인
        log.info("createDeliveryRoute, deliveryId: {}", deliveryId);
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 배송을 찾을 수 없습니다."));

        UUID fromHubId = delivery.getFromHubId();
        UUID toHubId = delivery.getToHubId();
        DeliveryAgentType fromType = DeliveryAgentType.HUB;
        DeliveryAgentType toType = DeliveryAgentType.COMPANY;

        // 허브이동관리 id 조회
        HubRouteResponseDto hubRoute = hubService.getHubRouteId(fromHubId, toHubId);
        log.info("getHubRoutes, {}, {}, {}", fromHubId, toHubId, hubRoute);
        // TODO: 테스트용 허브 경로
//        HubRouteResponseDto hubRoute = new HubRouteResponseDto(
//                UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac130002"),//UUID id
//                UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001"),//fromHubId;
//                UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120003"),//toHubId;
//                80,// totalDuration; // 소요시간
//                110.3// routeDistance; // 이동거리
//        );

        // 순번이 허브 첫 배송인 경우 해당 허브 소속의 배송담당자 순번 0인 사람 배정
        DeliveryAgent fromDeliveryAgent = new DeliveryAgent(deliveryAgentService.getDeliveryAgentByHubIdAndType(fromHubId, fromType)); // 공급업체 소속 허브 ID, 허브 타입
        DeliveryAgent toDeliveryAgent = new DeliveryAgent(deliveryAgentService.getDeliveryAgentByHubIdAndType(toHubId,toType)); // 수령업체 소속 허브 ID, 업체 타입

        // 배송 기록 생성
        DeliveryRoute deliveryRoute = DeliveryRoute.builder()
                .deliveryId(deliveryId)
                .estimatedDistance(hubRoute.getDistance())
                .estimatedTime(hubRoute.getDuration())
                .actualDistance(hubRoute.getDistance())
                .actualTime(hubRoute.getDuration())
                .fromHubId(delivery.getFromHubId())
                .toHubId(delivery.getToHubId())
                .companyDeliveryAgentId(toDeliveryAgent.getUserId())
                .hubDeliveryAgentId(fromDeliveryAgent.getUserId())
                .build();

        DeliveryRoute savedDeliveryRoute = deliveryRouteRepository.save(deliveryRoute);

        return new DeliveryRouteResponseDto(savedDeliveryRoute);
    }

}
