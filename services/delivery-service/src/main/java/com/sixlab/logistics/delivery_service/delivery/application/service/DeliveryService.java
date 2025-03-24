package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.CompanyClient;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.CompanyService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.CompanyResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
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
public class DeliveryService {

    private final HubService hubService;
    private final CompanyService companyService;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteService deliveryRouteService;

    // 배송 리스트 조회
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

        Delivery delivery;

        // 관리자와 업체담당자는 전부 가능
        if(currentRole == Role.MASTER || currentRole == Role.TRADE_PARTNER) {
            delivery = deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 정보를 찾을 수 없습니다."));
        }
        // 허브담당자는 본인의 허브 배송만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            delivery = deliveryRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //delivery = deliveryRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 조회 권한이 없습니다."));
        }
        // 배송담당자는 본인의 배송만 가능
        else if (currentRole == Role.DELIVERY_AGENT) {
            delivery = deliveryRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 조회 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 조회 권한이 없습니다.");
        }

        return new DeliveryResponseDto(delivery);
    }

    // 배송 수정
    @Transactional
    public DeliveryResponseDto updateDelivery(UUID id, DeliveryRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery;

        // 관리자와 업체담당자는 전부 가능
        if(currentRole == Role.MASTER || currentRole == Role.TRADE_PARTNER) {
            delivery = deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 정보를 찾을 수 없습니다."));
        }
        // 허브담당자는 본인의 허브 배송만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            delivery = deliveryRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //delivery = deliveryRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 수정 권한이 없습니다."));
        }
        // 배송담당자는 본인의 배송만 가능
        else if (currentRole == Role.DELIVERY_AGENT) {
            delivery = deliveryRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 수정 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 수정 권한이 없습니다.");
        }

        // 배송 정보 업데이트
        delivery.updateDelivery(requestDto);

        return new DeliveryResponseDto(delivery);
    }

    // 배송 상태 변경
    @Transactional
    public DeliveryStatusResponseDto updateDeliveryStatus(UUID id, DeliveryStatus status, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery;

        // 관리자와 업체담당자는 전부 가능
        if(currentRole == Role.MASTER || currentRole == Role.TRADE_PARTNER) {
            delivery = deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 정보를 찾을 수 없습니다."));
        }
        // 허브담당자는 본인의 허브 배송만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            delivery = deliveryRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //delivery = deliveryRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 수정 권한이 없습니다."));
        }
        // 배송담당자는 본인의 배송만 가능
        else if (currentRole == Role.DELIVERY_AGENT) {
            delivery = deliveryRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 수정 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 수정 권한이 없습니다.");
        }

        delivery.updateDeliveryStatus(status);

        return new DeliveryStatusResponseDto(delivery.getStatus());
    }

    // 배송 삭제
    @Transactional
    public DeliveryResponseDto deleteDelivery(UUID id, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        Delivery delivery;

        // 관리자와 업체담당자는 전부 가능
        if(currentRole == Role.MASTER || currentRole == Role.TRADE_PARTNER) {
            delivery = deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 정보를 찾을 수 없습니다."));
        }
        // 허브담당자는 본인의 허브 배송만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            delivery = deliveryRepository.findByIdAndToHubId(id, hubManager.getHubId())
            //delivery = deliveryRepository.findByIdAndToHubId(id, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 삭제 권한이 없습니다."));
        }
        // 배송담당자는 본인의 배송만 가능
        else if (currentRole == Role.DELIVERY_AGENT) {
            delivery = deliveryRepository.findByIdAndDeliveryAgentId(id, currentUserId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송의 삭제 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 삭제 권한이 없습니다.");
        }

        // 삭제 전 유효성 검사 - 배송대기 상태일 때만 삭제 가능
        if (delivery.getStatus() != DeliveryStatus.WAITING) {
            throw new IllegalStateException("배송대기 상태의 배송만 삭제할 수 있습니다.");
        }

        delivery.delete(currentUserId);

        return new DeliveryResponseDto(delivery);
    }

    // 배송 생성
//    @Transactional
//    public DeliveryResponseDto createDelivery(DeliveryRequestDto requestDto) {
//
//        // 요청 DTO에서 공급업체 ID와 수령업체 ID 가져오기
//        UUID supplierCompanyId = requestDto.getSupplierCompanyId();
//        UUID receiverCompanyId = requestDto.getReceiverCompanyId();
//
//        // 공급업체 정보 조회하여 출발지 허브 id 가져오기
//        CompanyResponseDto supplyCompany = companyClient.getCompanyId(supplierCompanyId);
//        if (supplyCompany == null) {
//            throw new ResourceNotFoundException("공급업체를 찾을 수 없습니다.");
//        }
//        UUID fromHubId = supplyCompany.getHubId();
//
//        // 수령 업체 정보 조회해서 도착지 허브 id 가져오기
//        CompanyResponseDto receiveCompany = companyClient.getCompanyId(receiverCompanyId);
//        if (receiveCompany == null) {
//            throw new ResourceNotFoundException("수령 업체를 찾을 수 없습니다.");
//        }
//        UUID toHubId = receiveCompany.getHubId();
//
//        // 허브이동관리 id 조회
//        HubRouteResponseDto hubRoute = hubClient.getHubRouteId(fromHubId, toHubId);
//        if (hubRoute == null) {
//            throw new ResourceNotFoundException("허브 이동 경로 정보를 찾을 수 없습니다.");
//        }
//
//        // 배송 생성
//        Delivery delivery = Delivery.builder()
//                .requestDto(requestDto)
//                .fromHubId(fromHubId)
//                .toHubId(toHubId)
//                .build();
//
//        // 배송 저장
//        Delivery savedDelivery = deliveryRepository.save(delivery);
//
//        // 배송 경로 생성
//        DeliveryRouteResponseDto responseDto = deliveryRouteService.createDeliveryRoute(delivery.getId());
//        Long companyDeliveryAgentId = responseDto.getCompanyDeliveryAgentId();
//
//        delivery.updateDeliveryAgent(companyDeliveryAgentId);
//
//        return new DeliveryResponseDto(savedDelivery);
//    }


    // 배송 생성
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
        log.info("savedDelivery id, {}", savedDelivery.getId());
        log.info("savedDelivery status, {}", savedDelivery.getStatus());
        log.info("savedDelivery deliveryAddress, {}", savedDelivery.getDeliveryAddress());
        log.info("savedDelivery receiveName, {}", savedDelivery.getReceiveName());
        log.info("savedDelivery companyDeliveryAgentId, {}", savedDelivery.getCompanyDeliveryAgentId());
        log.info("savedDelivery hubDeliveryAgentId, {}", savedDelivery.getHubDeliveryAgentId());
        log.info("savedDelivery fromHubId, {}", savedDelivery.getFromHubId());
        log.info("savedDelivery toHubId, {}", savedDelivery.getToHubId());

        return new DeliveryResponseDto(savedDelivery);
    }


}