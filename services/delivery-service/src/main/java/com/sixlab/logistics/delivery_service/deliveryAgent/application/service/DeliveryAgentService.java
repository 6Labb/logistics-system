package com.sixlab.logistics.delivery_service.deliveryAgent.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRequestDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryStatusResponseDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.CompanyClient;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubClient;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.CompanyResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubTotalRouteResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAgentService {

    private final DeliveryAgentRepository deliveryAgentRepository;

    // 배송 담당자 리스트 조회
    public Page<DeliveryAgentResponseDto> getAllDeliveryAgent(DeliveryAgentSearchDto searchDto, Pageable pageable) {
        // 권한 받아오기
            // 관리자는 전부 가능
            // 허브담당자는 본인의 허브 배송 담당자 리스트만 가능
            // 배송담당자는 본인의 배송 담당자 리스트만 가능

        Page<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findAll(pageable);
        return deliveryAgents.map(DeliveryAgentResponseDto::new);
    }

    // 배송 담당자 개별 조회
    public DeliveryAgentResponseDto getDeliveryAgent(Long userId) {
        // 권한 받아오기
            // 관리자는 전부 가능
            // 허브담당자는 본인의 허브 배송 담당자만 가능
            // 배송담당자는 본인의 배송 담당자만 가능

        // id로 배송 객체 찾기
        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 수정
    @Transactional
    public DeliveryAgentResponseDto updateDeliveryAgent(Long userId, DeliveryAgentRequestDto requestDto) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송 담당자만 가능

        // id로 배송 객체 찾기
        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        // 배송 정보 업데이트
        deliveryAgent.updateDeliveryAgent(requestDto);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 삭제
    @Transactional
    public DeliveryAgentResponseDto deleteDeliveryAgent(Long userId) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송 담당자만 가능

        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        deliveryAgent.delete(null);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 생성
    @Transactional
    public DeliveryAgentResponseDto createDeliveryAgent(DeliveryAgentRequestDto requestDto) {

        // 배송 담당자 생성
        DeliveryAgent deliveryAgent = DeliveryAgent.builder()
                .requestDto(requestDto)
                .build();

        // 배송 저장
        DeliveryAgent savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);

        return new DeliveryAgentResponseDto(savedDeliveryAgent);
    }

}
