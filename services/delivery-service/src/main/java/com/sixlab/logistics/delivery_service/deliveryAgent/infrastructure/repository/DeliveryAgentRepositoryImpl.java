package com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.repository;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.jpa.DeliveryJpaRepository;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.jpa.DeliveryAgentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryAgentRepositoryImpl implements DeliveryAgentRepository {

    private final DeliveryAgentJpaRepository deliveryAgentJpaRepository;

    @Override
    public Page<DeliveryAgent> findAll(Pageable pageable) {
        return deliveryAgentJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<DeliveryAgent> findByUserId(Long userId) {
        return deliveryAgentJpaRepository.findByUserId(userId);
    }

    @Override
    public DeliveryAgent save(DeliveryAgent deliveryAgent) {
        return deliveryAgentJpaRepository.save(deliveryAgent);
    }

}
