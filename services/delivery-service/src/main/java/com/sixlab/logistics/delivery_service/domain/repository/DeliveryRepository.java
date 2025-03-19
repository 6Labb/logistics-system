package com.sixlab.logistics.delivery_service.domain.repository;

import com.sixlab.logistics.delivery_service.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
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
