package com.sixlab.logistics.delivery_service.delivery.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteSearchDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {

// 배송 리스트 조회
    Page<DeliveryRouteResponseDto> searchDeliveryRouteList(DeliveryRouteSearchDto searchDto, Pageable pageable);

// 배송 개별 조회
    // id로 조회(관리자)
    Optional<DeliveryRoute> findById(UUID id);

    // 허브 담당자
    Optional<DeliveryRoute> findByIdAndToHubId(UUID id, UUID hubId);

    // 배송 담당자
    Optional<DeliveryRoute> findByIdAndDeliveryAgentId(UUID id, Long currentUserId);

    // 배송 생성
    DeliveryRoute save(DeliveryRoute deliveryRoute);
}
