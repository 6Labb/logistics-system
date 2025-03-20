package com.sixlab.logistics.delivery_service.delivery.application.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    private String deliveryAddress;
    private String receiveName;
    private UUID receiverCompanyId; // 수령업체 id
    private UUID supplierCompanyId; // 공급업체 id

}
