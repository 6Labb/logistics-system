package com.sixlab.logistics.delivery_service.delivery.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {

    // 배송상태로 조회
    //Optional<Delivery> findByStatus(DeliveryStatus status);

    // 소속 허브id로 조회
    //Page<Delivery> findByFromHubId(UUID fromHubId, Pageable pageable);

    // 배송담당자id로 조회
    //Page<Delivery> findByDeliveryAgentId(UUID deliveryAgentId, Pageable pageable);

    Page<DeliveryResponseDto> searchDeliveryList(DeliverySearchDto searchDto, Pageable pageable);

    // id로 조회
    Optional<Delivery> findById(UUID id);

    // 배송 삭제
    void delete(Delivery delivery);

    // 배송 생성
    Delivery save(Delivery delivery);

    // 마지막으로 할당된 배송순번 조회
    Optional<Delivery> findTopByToHubIdOrderByCreatedAtDesc(UUID toHubId);
}
