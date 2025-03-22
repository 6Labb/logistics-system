package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.CompanyClient;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubClient;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.CompanyResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final HubClient hubClient;
    private final CompanyClient companyClient;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final DeliveryRouteService deliveryRouteService;

    // 배송 리스트 조회
    public Page<DeliveryResponseDto> getAllDeliveries(DeliverySearchDto searchDto, Pageable pageable) {

        // 권한 받아오기
            // 관리자와 업체담당자는 전부 가능
            // 허브담당자는 본인의 허브 배송리스트만 가능
            // 배송담당자는 본인의 배송리스트만 가능

        return deliveryRepository.searchDeliveryList(searchDto, pageable);
    }

    // 배송 개별 조회
    public DeliveryResponseDto getDelivery(UUID id) {
        // 권한 받아오기
            // 관리자와 업체담당자는 전부 가능
            // 허브담당자는 본인의 허브 배송만 가능
            // 배송담당자는 본인의 배송만 가능

        // id로 배송 객체 찾기
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return new DeliveryResponseDto(delivery);
    }

    // 배송 수정
    @Transactional
    public DeliveryResponseDto updateDelivery(UUID id, DeliveryRequestDto requestDto) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송만 가능
            // 배송담당자는 본인의 배송만 가능

        // id로 배송 객체 찾기
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // 배송 정보 업데이트
        delivery.updateDelivery(requestDto);

        return new DeliveryResponseDto(delivery);
    }

    // 배송 상태 변경
    @Transactional
    public DeliveryStatusResponseDto updateDeliveryStatus(UUID id, DeliveryStatus status) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송만 가능
            // 배송담당자는 본인의 배송만 가능

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        delivery.updateDeliveryStatus(status);

        return new DeliveryStatusResponseDto(delivery.getStatus());
    }

    // 배송 삭제
    @Transactional
    public DeliveryResponseDto deleteDelivery(UUID id) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송만 가능

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // 삭제 전 유효성 검사 - 배송대기 상태일 때만 삭제 가능
        if (delivery.getStatus() != DeliveryStatus.WAITING) {
            throw new IllegalStateException("배송대기 상태의 배송만 삭제할 수 있습니다.");
        }

        delivery.delete(null);

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
        UUID fromHubId = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
        UUID toHubId = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120003");

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

        delivery.updateDeliveryAgent(companyDeliveryAgentId);

        return new DeliveryResponseDto(savedDelivery);
    }


}
