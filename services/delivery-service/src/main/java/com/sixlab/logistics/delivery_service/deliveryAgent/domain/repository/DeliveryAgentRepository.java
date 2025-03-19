package com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryAgentRepository {

    Page<DeliveryAgent> findAll(Pageable pageable);

    Optional<DeliveryAgent> findByUserId(Long userId);

    DeliveryAgent save(DeliveryAgent deliveryAgent);
}
