package com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAgentRepository {

    Page<DeliveryAgentResponseDto> searchDeliveryAgentList(DeliveryAgentSearchDto searchDto, Pageable pageable);

    Optional<DeliveryAgent> findByUserId(Long userId);

    DeliveryAgent save(DeliveryAgent deliveryAgent);

    // 배송 담당자 중 업체담당배송담당자 조회(배송생성 api에서 사용)
    List<DeliveryAgent> findByHubIdAndTypeOrderByDeliverySequenceAsc(UUID toHubId, DeliveryAgentType deliveryAgentType);
}
