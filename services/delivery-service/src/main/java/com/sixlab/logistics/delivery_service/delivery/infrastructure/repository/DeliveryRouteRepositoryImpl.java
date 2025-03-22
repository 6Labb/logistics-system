package com.sixlab.logistics.delivery_service.delivery.infrastructure.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteSearchDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRouteRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.jpa.DeliveryRouteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteRepositoryImpl implements DeliveryRouteRepository {

    private final DeliveryRouteJpaRepository jpaRepository;
    private final DeliveryRouteQueryRepository deliveryRouteQueryRepository;

    @Override
    public Page<DeliveryRouteResponseDto> searchDeliveryRouteList(DeliveryRouteSearchDto searchDto, Pageable pageable) {
        return deliveryRouteQueryRepository.searchDeliveryRouteList(searchDto, pageable, null, null);
    }

    @Override
    public Optional<DeliveryRoute> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<DeliveryRoute> findByIdAndToHubId(UUID id, UUID hubId) {
        return jpaRepository.findByIdAndToHubId(id, hubId);
    }

    @Override
    public Optional<DeliveryRoute> findByIdAndDeliveryAgentId(UUID id, Long currentUserId) {
        Optional<DeliveryRoute> deliveryRoute = jpaRepository.findByIdAndHubDeliveryAgentId(id, currentUserId);
        if (deliveryRoute.isPresent()) {
            return deliveryRoute;
        }
        return jpaRepository.findByIdAndCompanyDeliveryAgentId(id, currentUserId);
    }

    @Override
    public DeliveryRoute save(DeliveryRoute deliveryRoute) {
        return jpaRepository.save(deliveryRoute);
    }

}
