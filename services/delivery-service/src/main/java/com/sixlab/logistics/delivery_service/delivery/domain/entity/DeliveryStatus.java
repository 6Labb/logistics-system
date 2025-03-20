package com.sixlab.logistics.delivery_service.delivery.domain.entity;

public enum DeliveryStatus {
    WAITING, // 배송대기
    PENDING_HUB, // 허브대기중
    MOVE_TO_HUB, // 허브이동중
    ARRIVED_HUB, // 경유허브도착
    OUT_FOR_DELIVERY, // 배송중
    MOVE_TO_COMPANY, // 업체이동중
    COMPLETED // 배송완료
}
