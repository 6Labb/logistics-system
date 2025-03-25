package com.sixlab.logistics.delivery_service.delivery.domain.model;

public enum DeliveryRouteStatus {
    WAITING, // 배송대기
    PENDING_HUB, // 허브이동대기중
    MOVE_TO_HUB, // 허브이동중
    ARRIVED_HUB, // 경유허브도착
    OUT_FOR_DELIVERY, // 배송중
    COMPLETED, // 배송완료
    CANCELED // 배송취소
}