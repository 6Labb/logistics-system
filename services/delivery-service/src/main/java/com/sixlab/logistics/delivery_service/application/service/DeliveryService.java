package com.sixlab.logistics.delivery_service.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.delivery_service.application.dto.DeliveryRequestDto;
import com.sixlab.logistics.delivery_service.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.application.dto.DeliveryStatusResponseDto;
import com.sixlab.logistics.delivery_service.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.infrastructure.client.CompanyClient;
import com.sixlab.logistics.delivery_service.infrastructure.client.HubClient;
import com.sixlab.logistics.delivery_service.infrastructure.client.UserClient;
import com.sixlab.logistics.delivery_service.infrastructure.client.dto.CompanyResponseDto;
import com.sixlab.logistics.delivery_service.infrastructure.client.dto.HubTotalRouteResponseDto;
import com.sixlab.logistics.delivery_service.infrastructure.client.dto.UserDeliveryAgentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final UserClient userClient;
    private final HubClient hubClient;
    private final CompanyClient companyClient;
    private final DeliveryRepository deliveryRepository;

    // 배송 리스트 조회
    public Page<DeliveryResponseDto> getAllDeliveries(DeliverySearchDto searchDto, Pageable pageable) {
        // 권한 받아오기
            // 관리자와 업체담당자는 전부 가능
            // 허브담당자는 본인의 허브 배송리스트만 가능
            // 배송담당자는 본인의 배송리스트만 가능

        Page<Delivery> deliveries = deliveryRepository.findAll(pageable);
        return deliveries.map(DeliveryResponseDto::new);
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
    @Transactional
    public DeliveryResponseDto createDelivery(DeliveryRequestDto requestDto) {

        // 요청 DTO에서 공급업체 ID와 수령업체 ID 가져오기
        UUID supplierCompanyId = requestDto.getSupplierCompanyId();
        UUID receiverCompanyId = requestDto.getReceiverCompanyId();

        // 공급업체 정보 조회하여 출발지 허브 id 가져오기
        CompanyResponseDto supplyCompany = companyClient.getCompanyId(supplierCompanyId);
        if (supplyCompany == null) {
            throw new ResourceNotFoundException("공급업체를 찾을 수 없습니다.");
        }
        UUID fromHubId = supplyCompany.getHubId();

        // 수령 업체 정보 조회해서 도착지 허브 id 가져오기
        CompanyResponseDto receiveCompany = companyClient.getCompanyId(receiverCompanyId);
        if (receiveCompany == null) {
            throw new ResourceNotFoundException("수령 업체를 찾을 수 없습니다.");
        }
        UUID toHubId = receiveCompany.getHubId();

        // 허브총이동관리 id 조회
        HubTotalRouteResponseDto hubTotalRoute = hubClient.getHubTotalRouteId(fromHubId, toHubId);
        if (hubTotalRoute == null) {
            throw new ResourceNotFoundException("허브 이동 경로 정보를 찾을 수 없습니다.");
        }
        UUID hubTotalRouteId = hubTotalRoute.getHubTotalRouteId();

        // 배송담당자 조회
        List<UserDeliveryAgentDto> agents = userClient.getDeliveryAgentsByHub(toHubId);
        if (agents.isEmpty()) {
            throw new ResourceNotFoundException("배송 담당자를 찾을 수 없습니다.");
        }

        // 배송순번이 가장 낮은 담당자를 선택
        UserDeliveryAgentDto deliveryAgent = agents.stream()
                .sorted(Comparator.comparingInt(UserDeliveryAgentDto::getDeliverySequence))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("배송 담당자 배정 실패"));

        // 배송담당자 순환으로 선택
        // 알고리즘으로 처리

        // 배송 생성
        Delivery delivery = Delivery.builder()
                .requestDto(requestDto)
                .fromHubId(fromHubId)
                .toHubId(toHubId)
                .hubTotalRouteId(hubTotalRouteId)
                .companyDeliveryAgentId(deliveryAgent.getDeliveryAgentId())
                .build();

        // 배송 저장
        Delivery savedDelivery = deliveryRepository.save(delivery);

        return new DeliveryResponseDto(savedDelivery);
    }

}
