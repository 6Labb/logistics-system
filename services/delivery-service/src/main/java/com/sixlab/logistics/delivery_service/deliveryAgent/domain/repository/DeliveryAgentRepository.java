package com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAgentRepository {

// 배송 담당자 리스트 조회
    Page<DeliveryAgentResponseDto> searchDeliveryAgentListForMaster(DeliveryAgentSearchDto searchDto, Pageable pageable);

    Page<DeliveryAgentResponseDto> searchDeliveryAgentListForHubMaster(DeliveryAgentSearchDto searchDto, Pageable pageable, UUID hubId);

// 배송 담당자 개별 조회
    // id로 조회(관리자, 배송담당자 본인)
    Optional<DeliveryAgent> findByUserId(Long userId);

    // 허브 담당자
    Optional<DeliveryAgent> findByUserIdAndHubId(Long userId, UUID hubId);

    DeliveryAgent save(DeliveryAgent deliveryAgent);

    // 배송 담당자 중 업체담당배송담당자 조회(배송생성 api에서 사용)
    List<DeliveryAgent> findByHubIdAndTypeOrderByDeliverySequenceAsc(UUID toHubId, DeliveryAgentType deliveryAgentType);
}
