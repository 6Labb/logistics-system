package com.sixlab.logistics.order_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RequestDeliveryRegisterDto {
    private String deliveryAddress; // 배송지
    private String receiveName; // 수령인
    private UUID receiverCompanyId; // 수령업체 id
    private UUID supplierCompanyId; // 공급업체 id
}
