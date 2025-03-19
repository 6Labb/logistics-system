package com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryAgentJpaRepository extends JpaRepository<DeliveryAgent, Long> {

    // userId로 조회
    Optional<DeliveryAgent> findByUserId(Long userId);

    // 배송담당자 리스트 조회
    Page<DeliveryAgent> findAll(Pageable pageable);

    // 배송담당자 저장
    DeliveryAgent save(DeliveryAgent deliveryAgent);

    // 배송담당자 삭제
    void delete(DeliveryAgent deliveryAgent);

}
