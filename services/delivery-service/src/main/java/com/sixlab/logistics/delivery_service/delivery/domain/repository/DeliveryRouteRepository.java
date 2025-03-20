package com.sixlab.logistics.delivery_service.delivery.domain.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryRouteSearchDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {

    Page<DeliveryRouteResponseDto> searchDeliveryRouteList(DeliveryRouteSearchDto searchDto, Pageable pageable);

    Optional<DeliveryRoute> findById(UUID id);

    void delete(DeliveryRoute deliveryRoute);

    DeliveryRoute save(DeliveryRoute deliveryRoute);
}
