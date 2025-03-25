package com.sixlab.logistics.delivery_service.delivery.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.delivery.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID> {

    // 배송 개별 조회
    // id로 조회(관리자)
    Optional<Delivery> findById(UUID id);

    // 허브 담당자
    Optional<Delivery> findByIdAndToHubId(UUID id, UUID hubId);

    // 배송 담당자 - 허브
    Optional<Delivery> findByIdAndHubDeliveryAgentId(UUID id, Long hubDeliveryAgentId);

    // 배송 담당자 - 업체
    Optional<Delivery> findByIdAndCompanyDeliveryAgentId(UUID id, Long companyDeliveryAgentId);

    // 배송 생성
    Delivery save(Delivery delivery);

}
