package com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryAgentJpaRepository extends JpaRepository<DeliveryAgent, Long> {

    // userId로 조회
    Optional<DeliveryAgent> findByUserId(Long userId);

    // 배송담당자 저장
    DeliveryAgent save(DeliveryAgent deliveryAgent);

    // 배송담당자 삭제
    void delete(DeliveryAgent deliveryAgent);

    // 배송 담당자 중 업체담당배송담당자 조회(배송생성 api에서 사용)
    List<DeliveryAgent> findByHubIdAndTypeOrderByDeliverySequenceAsc(UUID toHubId, DeliveryAgentType deliveryAgentType);

}
