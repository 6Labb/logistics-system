package com.sixlab.logistics.delivery_service.domain.repository;

import com.sixlab.logistics.delivery_service.domain.entity.DeliveryRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {

    Page<DeliveryRoute> findAll(Pageable pageable);

    Page<DeliveryRoute> findAllByDeliveryId(Pageable pageable, UUID deliveryId);

    Optional<DeliveryRoute> findById(UUID id);

    void delete(DeliveryRoute deliveryRoute);


}
