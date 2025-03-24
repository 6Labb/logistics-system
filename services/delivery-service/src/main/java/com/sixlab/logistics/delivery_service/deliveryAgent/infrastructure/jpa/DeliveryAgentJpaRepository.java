package com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryAgentJpaRepository extends JpaRepository<DeliveryAgent, Long> {

    // 배송 담당자 개별 조회
    // id로 조회(관리자, 배송담당자 본인)
    Optional<DeliveryAgent> findByUserId(Long userId);

    // 허브 담당자
    Optional<DeliveryAgent> findByUserIdAndHubId(Long userId, UUID hubId);

    // 배송담당자 저장
    DeliveryAgent save(DeliveryAgent deliveryAgent);

    // 배송담당자 삭제
    void delete(DeliveryAgent deliveryAgent);

    // 배송 담당자 중 업체담당배송담당자 조회(배송생성 api에서 사용)
    List<DeliveryAgent> findByHubIdAndTypeOrderByDeliverySequenceAsc(UUID toHubId, DeliveryAgentType deliveryAgentType);

}
