package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRouteStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

    private final DeliveryRouteRepository deliveryRouteRepository;

    // 배송 경로 목록 조회
    public Page<DeliveryRouteResponseDto> getAllDeliveryRoute(DeliveryRouteSearchDto searchDto, Pageable pageable) {

        return deliveryRouteRepository.searchDeliveryRouteList(searchDto, pageable);
    }

    // 특정 배송 모든 경로 조회
    public Page<DeliveryRouteResponseDto> getAllDeliveryRouteByDeliveryId(DeliveryRouteSearchDto searchDto, Pageable pageable, UUID deliveryId) {

        //Page<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findAllByDeliveryId(pageable, deliveryId);
        //return deliveryRoutes.map(DeliveryRouteResponseDto::new);
        return deliveryRouteRepository.searchDeliveryRouteList(searchDto, pageable);
    }

    // 특정 배송 모든 경로 개별 조회
    public DeliveryRouteResponseDto getDeliveryRouteByDeliveryId(UUID deliveryId, UUID id) {

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송 경로 수정
    @Transactional
    public DeliveryRouteResponseDto updateDeliveryRoute(UUID deliveryId, UUID id, DeliveryRouteRequestDto requestDto) {
        // 권한 받아오기
            // 관리자
            // 허브담당자

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        deliveryRoute.updateDeliveryRoute(requestDto);
        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송 경로 상태 변경
    @Transactional
    public DeliveryRouteStatusResponseDto updateDeliveryRouteStatus(UUID deliveryId, UUID id, DeliveryRouteStatus status) {
        // 권한 받아오기
            // 관리자
            // 허브담당자
            // 배송담당자

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        deliveryRoute.updateDeliveryRouteStatus(status);
        return new DeliveryRouteStatusResponseDto(deliveryRoute.getStatus());
    }

    // 배송 경로 삭제
    @Transactional
    public DeliveryRouteResponseDto deleteDeliveryRoute(UUID deliveryId, UUID id) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송만 가능

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // 삭제 전 유효성 검사 - 배송대기 상태일 때만 삭제 가능
        if (deliveryRoute.getStatus() != DeliveryRouteStatus.WAITING) {
            throw new IllegalStateException("배송대기 상태의 배송기록만 삭제할 수 있습니다.");
        }

        // 해당 배송의 경로인지 확인
//        if (!deliveryRoute.getDelivery().getId().equals(deliveryId)) {
//            throw new IllegalArgumentException("해당 배송의 경로가 아닙니다.");
//        }

        deliveryRoute.delete(null);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송경로 생성


}
