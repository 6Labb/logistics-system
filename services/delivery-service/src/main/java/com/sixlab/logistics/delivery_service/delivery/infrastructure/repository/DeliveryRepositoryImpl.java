package com.sixlab.logistics.delivery_service.delivery.infrastructure.repository;

import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliveryResponseDto;
import com.sixlab.logistics.delivery_service.delivery.application.dto.DeliverySearchDto;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.jpa.DeliveryJpaRepository;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.repository.DeliveryAgentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;
    private final DeliveryQueryRepository deliveryQueryRepository;

    // 관리자, 업체 담당자용 배송 목록 조회
    @Override
    public Page<DeliveryResponseDto> searchDeliveryListForMaster(DeliverySearchDto searchDto, Pageable pageable) {
        return deliveryQueryRepository.searchDeliveryList(searchDto, pageable, null, null);
    }

    // 배송 담당자용 배송 목록 조회
    @Override
    public Page<DeliveryResponseDto> searchDeliveryListForDeliveryAgent(DeliverySearchDto searchDto, Pageable pageable, Long deliveryAgentId) {
        return deliveryQueryRepository.searchDeliveryList(searchDto, pageable, deliveryAgentId, null);
    }

    // 허브 관리자용 배송 목록 조회
    @Override
    public Page<DeliveryResponseDto> searchDeliveryListForHubManager(DeliverySearchDto searchDto, Pageable pageable, UUID ownHubId) {
        return deliveryQueryRepository.searchDeliveryList(searchDto, pageable, null, ownHubId);
    }

    @Override
    public Optional<Delivery> findById(UUID id) {
        return deliveryJpaRepository.findById(id);
    }

    @Override
    public Optional<Delivery> findByIdAndToHubId(UUID id, UUID hubId) {
        return deliveryJpaRepository.findByIdAndToHubId(id, hubId);
    }

    @Override
    public Optional<Delivery> findByIdAndDeliveryAgentId(UUID id, Long currentUserId) {
        Optional<Delivery> delivery = deliveryJpaRepository.findByIdAndHubDeliveryAgentId(id, currentUserId);
        if (delivery.isPresent()) {
            return delivery;
        }
        return deliveryJpaRepository.findByIdAndCompanyDeliveryAgentId(id, currentUserId);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }

}
