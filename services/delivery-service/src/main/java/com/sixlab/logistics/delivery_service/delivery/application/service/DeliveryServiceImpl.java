package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.model.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.CompanyService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.CompanyResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
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
@Slf4j(topic = "DeliveryService")
public class DeliveryServiceImpl implements DeliveryService {

    private final HubService hubService;
    private final CompanyService companyService;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteService deliveryRouteService;

    // 배송 리스트 조회
    @Override
    public Page<DeliveryResponseDto> getAllDeliveries(DeliverySearchDto searchDto, Pageable pageable, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        // 관리자와 업체담당자는 전부 가능
        if (currentRole == Role.MASTER || currentRole == Role.TRADE_PARTNER) {
            return deliveryRepository.searchDeliveryListForMaster(searchDto, pageable);
        }

        // 허브담당자는 본인의 허브 배송리스트만 가능
        if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 소속허브id를 찾을 수 없습니다.");
            }
            return deliveryRepository.searchDeliveryListForHubManager(searchDto, pageable, hubManager.getHubId());
            //return deliveryRepository.searchDeliveryListForHubManager(searchDto, pageable, hubManager);
        }
        // 배송담당자는 본인의 배송리스트만 가능
        if (currentRole == Role.DELIVERY_AGENT) {
            return deliveryRepository.searchDeliveryListForDeliveryAgent(searchDto, pageable, currentUserId);
        }

        throw new AccessDeniedException("배송 리스트 조회 권한이 없습니다.");
    }

    // 배송 개별 조회
    public DeliveryResponseDto getDelivery(UUID id, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery = findDeliveryWithAuthorization(id, currentRole, currentUserId, "조회");

        return new DeliveryResponseDto(delivery);
    }

    // 배송 수정
    @Override
    @Transactional
    public DeliveryResponseDto updateDelivery(UUID id, DeliveryRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery = findDeliveryWithAuthorization(id, currentRole, currentUserId, "수정");

        delivery.updateDelivery(requestDto);

        return new DeliveryResponseDto(delivery);
    }

    // 배송 상태 변경
    @Override
    @Transactional
    public DeliveryStatusResponseDto updateDeliveryStatus(UUID id, DeliveryStatus status, UserDetailsImpl userDetails) {
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery = findDeliveryWithAuthorization(id, currentRole, currentUserId, "상태 변경");

        delivery.updateDeliveryStatus(status);

        return new DeliveryStatusResponseDto(delivery.getStatus());
    }

    // 배송 삭제
    @Override
    @Transactional
    public DeliveryResponseDto deleteDelivery(UUID id, UserDetailsImpl userDetails) {
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery = findDeliveryWithAuthorization(id, currentRole, currentUserId, "삭제");

        // 삭제 전 유효성 검사 - 배송대기 상태일 때만 삭제 가능
        if (delivery.getStatus() != DeliveryStatus.WAITING) {
            throw new IllegalStateException("배송대기 상태의 배송만 삭제할 수 있습니다.");
        }

        delivery.delete(currentUserId);

        return new DeliveryResponseDto(delivery);
    }

    // 배송 생성
    @Override
    @Transactional
    public DeliveryResponseDto createDelivery(DeliveryRequestDto requestDto) {
        //TODO: 테스트 - 수령업체id, 공급업체id의 소속허브id
//        UUID fromHubId = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
//        UUID toHubId = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120002");

        // 요청 DTO에서 공급업체 ID와 수령업체 ID 가져오기
        UUID supplierCompanyId = requestDto.getSupplierCompanyId();
        UUID receiverCompanyId = requestDto.getReceiverCompanyId();

        // 공급업체 정보 조회하여 출발지 허브 id 가져오기
        CompanyResponseDto hubId = companyService.getCompanyId(supplierCompanyId, receiverCompanyId);
        if (hubId == null) {
            throw new ResourceNotFoundException("소속허브Id를 찾을 수 없습니다.");
        }
        UUID fromHubId = hubId.getDepartureHubId();
        UUID toHubId = hubId.getArrivalHubId();

        // 허브이동관리 id 조회
        HubRouteResponseDto hubRoute = hubService.getHubRouteId(fromHubId, toHubId);
        if (hubRoute == null) {
            throw new ResourceNotFoundException("허브 이동 경로 정보를 찾을 수 없습니다.");
        }

        // 배송 생성
        Delivery delivery = Delivery.builder()
                .requestDto(requestDto)
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .build();

        // 배송 저장
        Delivery savedDelivery = deliveryRepository.save(delivery);
        // 배송 경로 생성
        DeliveryRouteResponseDto responseDto = deliveryRouteService.createDeliveryRoute(delivery.getId());
        Long companyDeliveryAgentId = responseDto.getCompanyDeliveryAgentId();
        Long hubDeliveryAgentId = responseDto.getHubDeliveryAgentId();

        delivery.updateDeliveryAgent(companyDeliveryAgentId, hubDeliveryAgentId);

        return new DeliveryResponseDto(savedDelivery);
    }

    // 권한 공통 로직
    private Delivery findDeliveryWithAuthorization(UUID id, Role currentRole, Long currentUserId, String operation) {
        // 관리자와 업체담당자는 전부 가능
        if (currentRole.equals(Role.MASTER) || currentRole.equals(Role.TRADE_PARTNER)) {
            return deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 정보를 찾을 수 없습니다."));
        }

        // 허브담당자는 본인의 허브 배송만 가능
        if (currentRole.equals(Role.HUB_MANAGER)) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            return deliveryRepository.findByIdAndToHubId(id, hubManager.getHubId())
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 " + operation + " 권한이 없습니다."));
        }

        // 배송담당자는 본인의 배송만 가능
        if (currentRole.equals(Role.DELIVERY_AGENT)) {
            return deliveryRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 " + operation + " 권한이 없습니다."));
        }

        throw new AccessDeniedException("배송 " + operation + " 권한이 없습니다.");
    }
}
