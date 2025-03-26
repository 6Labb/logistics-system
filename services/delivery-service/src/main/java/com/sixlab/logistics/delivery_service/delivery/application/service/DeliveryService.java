package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeliveryService {


    // 배송 리스트 조회
    Page<DeliveryResponseDto> getAllDeliveries(DeliverySearchDto searchDto, Pageable pageable, UserDetailsImpl userDetails);

    // 배송 개별 조회
    DeliveryResponseDto getDelivery(UUID id, UserDetailsImpl userDetails);

    // 배송 수정
    DeliveryResponseDto updateDelivery(UUID id, DeliveryRequestDto requestDto, UserDetailsImpl userDetails);

    // 배송 상태 변경
    DeliveryStatusResponseDto updateDeliveryStatus(UUID id, DeliveryStatus status, UserDetailsImpl userDetails);

    // 배송 삭제
    DeliveryResponseDto deleteDelivery(UUID id, UserDetailsImpl userDetails);

    // 배송 생성
    DeliveryResponseDto createDelivery(DeliveryRequestDto requestDto);


}