package com.sixlab.logistics.order_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RequestDeliveryRegisterDto {
    // 배송지, 주문 id, 수령업체이름(수령업체명), 수령인 slackid
    private UUID orderId; // 주문 id;
    private String address; // 배송지
    private String receiverCompanyName; // 수령업체이름(수령업체명)
    private String receiverCompanySlackId; // 수령업체의 slackId
}
