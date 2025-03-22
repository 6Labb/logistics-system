package com.sixlab.logistics.delivery_service.delivery.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRouteJpaRepository extends JpaRepository<DeliveryRoute, UUID> {

    Page<DeliveryRoute> findAllByDeliveryId(Pageable pageable, UUID deliveryId);

    // 허브 담당자
    Optional<DeliveryRoute> findByIdAndToHubId(UUID id, UUID hubId);

    // 배송 담당자 - 허브
    Optional<DeliveryRoute> findByIdAndHubDeliveryAgentId(UUID id, Long hubDeliveryAgentId);

    // 배송 담당자 - 업체
    Optional<DeliveryRoute> findByIdAndCompanyDeliveryAgentId(UUID id, Long companyDeliveryAgentId);

    // 배송 생성
    DeliveryRoute save(DeliveryRoute deliveryRoute);

}
