package com.sixlab.logistics.delivery_service.infrastructure.repository;

import com.sixlab.logistics.delivery_service.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import com.sixlab.logistics.delivery_service.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.infrastructure.jpa.DeliveryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public Optional<Delivery> findById(UUID id) {
        return deliveryJpaRepository.findById(id);
    }

    @Override
    public Page<Delivery> findAll(Pageable pageable) {
        return deliveryJpaRepository.findAll(pageable);
    }

    /*
    @Override
    public Optional<Delivery> findByStatus(DeliveryStatus status) {
        return deliveryJpaRepository.findByStatus(status);
    }

    @Override
    public Page<Delivery> findByFromHubId(UUID hubId, Pageable pageable) {
        return deliveryJpaRepository.findByHubId(hubId, pageable);
    }

    @Override
    public Page<Delivery> findByCompanyDeliveryAgentId(UUID companyDeliveryAgentId, Pageable pageable) {
        return deliveryJpaRepository.findByCompanyDeliveryAgentId(companyDeliveryAgentId, pageable);
    }
     */

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }

    @Override
    public void delete(Delivery delivery) {
        deliveryJpaRepository.delete(delivery);
    }

}
