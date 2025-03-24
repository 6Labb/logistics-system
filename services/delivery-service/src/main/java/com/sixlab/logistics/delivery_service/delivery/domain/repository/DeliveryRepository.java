package com.sixlab.logistics.delivery_service.delivery.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {

// 배송 리스트 조회
    // 배송 리스트 조회
    Page<DeliveryResponseDto> searchDeliveryListForMaster(DeliverySearchDto searchDto, Pageable pageable);

    Page<DeliveryResponseDto> searchDeliveryListForDeliveryAgent(DeliverySearchDto searchDto, Pageable pageable, Long deliveryAgentId);

    Page<DeliveryResponseDto> searchDeliveryListForHubManager(DeliverySearchDto searchDto, Pageable pageable, UUID ownHubId);

// 배송 개별 조회
    // id로 조회(관리자)
    Optional<Delivery> findById(UUID id);

    // 허브 담당자
    Optional<Delivery> findByIdAndToHubId(UUID id, UUID hubId);

    // 배송 담당자
    Optional<Delivery> findByIdAndDeliveryAgentId(UUID id, Long currentUserId);

    // 배송 생성
    Delivery save(Delivery delivery);

}
