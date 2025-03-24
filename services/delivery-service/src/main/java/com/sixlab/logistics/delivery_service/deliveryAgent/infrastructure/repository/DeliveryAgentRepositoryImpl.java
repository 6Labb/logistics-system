package com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.repository;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.jpa.DeliveryJpaRepository;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import com.sixlab.logistics.delivery_service.deliveryAgent.infrastructure.jpa.DeliveryAgentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryAgentRepositoryImpl implements DeliveryAgentRepository {

    private final DeliveryAgentJpaRepository deliveryAgentJpaRepository;
    private final DeliveryAgentQueryRepository deliveryAgentQueryRepository;

    // 관리자
    @Override
    public Page<DeliveryAgentResponseDto> searchDeliveryAgentListForMaster(DeliveryAgentSearchDto searchDto, Pageable pageable) {
        return deliveryAgentQueryRepository.searchDeliveryAgentList(searchDto, pageable);
    }

    // 허브 관리자
    @Override
    public Page<DeliveryAgentResponseDto> searchDeliveryAgentListForHubMaster(DeliveryAgentSearchDto searchDto, Pageable pageable, UUID hubId) {
        return deliveryAgentQueryRepository.searchDeliveryAgentList(searchDto, pageable);
    }

    @Override
    public Optional<DeliveryAgent> findByUserId(Long userId) {
        return deliveryAgentJpaRepository.findByUserId(userId);
    }

    @Override
    public Optional<DeliveryAgent> findByUserIdAndHubId(Long userId, UUID hubId) {
        return deliveryAgentJpaRepository.findByUserIdAndHubId(userId, hubId);
    }

    @Override
    public DeliveryAgent save(DeliveryAgent deliveryAgent) {
        return deliveryAgentJpaRepository.save(deliveryAgent);
    }

    @Override
    public List<DeliveryAgent> findByHubIdAndTypeOrderByDeliverySequenceAsc(UUID toHubId, DeliveryAgentType deliveryAgentType) {
        return deliveryAgentJpaRepository.findByHubIdAndTypeOrderByDeliverySequenceAsc(toHubId, deliveryAgentType);
    }

}
