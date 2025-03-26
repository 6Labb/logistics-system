package com.sixlab.logistics.delivery_service.deliveryAgent.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface DeliveryAgentService {

    // 배송 담당자 리스트 조회
    Page<DeliveryAgentResponseDto> getAllDeliveryAgent(DeliveryAgentSearchDto searchDto, Pageable pageable, UserDetailsImpl userDetails);

    // 배송 담당자 개별 조회
    DeliveryAgentResponseDto getDeliveryAgent(Long userId, UserDetailsImpl userDetails);

    // 배송 담당자 조회 by 허브 ID - 배송경로생성에 사용
    DeliveryAgentResponseDto getDeliveryAgentByHubIdAndType(UUID hubId, DeliveryAgentType type);

    // 배송 담당자 수정
    DeliveryAgentResponseDto updateDeliveryAgent(Long userId, DeliveryAgentRequestDto requestDto, UserDetailsImpl userDetails);

    // 배송 담당자 삭제
    DeliveryAgentResponseDto deleteDeliveryAgent(Long userId, UserDetailsImpl userDetails);

    // 배송 담당자 생성
    DeliveryAgentResponseDto createDeliveryAgent(DeliveryAgentRequestDto requestDto, UserDetailsImpl userDetails);

}
