package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.model.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryRouteStatus;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeliveryRouteService {

    // 배송 경로 목록 조회
    public Page<DeliveryRouteResponseDto> getAllDeliveryRoute(DeliveryRouteSearchDto searchDto, Pageable pageable);

    // 배송 개별 조회
    public DeliveryRouteResponseDto getDeliveryRouteByDeliveryId(UUID deliveryId, UUID id);

    // 배송 경로 수정
    public DeliveryRouteResponseDto updateDeliveryRoute(UUID deliveryId, UUID id, DeliveryRouteRequestDto requestDto, UserDetailsImpl userDetails);

    // 배송 경로 상태 변경
    public DeliveryRouteStatusResponseDto updateDeliveryRouteStatus(UUID deliveryId, UUID id, DeliveryRouteStatus status, UserDetailsImpl userDetails);

    // 배송 경로 삭제
    public DeliveryRouteResponseDto deleteDeliveryRoute(UUID deliveryId, UUID id, UserDetailsImpl userDetails);

    // 배송경로 생성
    public DeliveryRouteResponseDto createDeliveryRoute(UUID deliveryId);

}
