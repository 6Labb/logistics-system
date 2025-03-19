package com.sixlab.logistics.delivery_service.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.domain.repository.DeliveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID> {

    // 배송상태 조회
    //Optional<Delivery> findByStatus(DeliveryStatus status);

    // 소속 허브id로 조회
    //Page<Delivery> findByHubId(UUID hubId, Pageable pageable);

    // 배송담당자id로 조회
    //Page<Delivery> findByCompanyDeliveryAgentId(UUID companyDeliveryAgentId, Pageable pageable);

    // 배송리스트 조회
    Page<Delivery> findAll(Pageable pageable);

    // id로 조회
    Optional<Delivery> findById(UUID id);

    // 배송 삭제
    void delete(Delivery delivery);

    // 배송 생성
    Delivery save(Delivery delivery);

}
