package com.sixlab.logistics.delivery_service.infrastructure.jpa;

import com.sixlab.logistics.delivery_service.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.domain.repository.DeliveryRouteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRouteJpaRepository extends JpaRepository<DeliveryRoute, UUID> {

    Page<DeliveryRoute> findAll(Pageable pageable);

    Page<DeliveryRoute> findAllByDeliveryId(Pageable pageable, UUID deliveryId);

    Optional<DeliveryRoute> findById(UUID id);

    void delete(DeliveryRoute deliveryRoute);

}
