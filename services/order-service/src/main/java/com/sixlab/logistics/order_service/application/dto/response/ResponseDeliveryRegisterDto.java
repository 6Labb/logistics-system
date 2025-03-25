package com.sixlab.logistics.order_service.application.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDeliveryRegisterDto {
        private UUID id; // private UUID id; // 배송 id
        private DeliveryStatus status; // private DeliveryStatus status;
        private String deliveryAddress; // private String deliveryAddress; // 배송지
        private String receiveName; // private String receiveName; // 수령인
        private Long companyDeliveryAgentId; // private Long deliveryAgentId;
        private Long hubDeliveryAgentId; // private UUID hubRouteId;
        private UUID fromHubId; // private UUID fromHubId;
        private UUID toHubId; // private UUID toHubId;

    public enum DeliveryStatus {
        WAITING, // 배송대기
        PENDING_HUB, // 허브대기중
        MOVE_TO_HUB, // 허브이동중
        ARRIVED_HUB, // 경유허브도착
        OUT_FOR_DELIVERY, // 배송중
        MOVE_TO_COMPANY, // 업체이동중
        COMPLETED // 배송완료
    }
}
