package com.sixlab.logistics.delivery_service.application.dto;

import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    private UUID id;
    private DeliveryStatus status;
    private String deliveryAddress;
    private String receiveName;
    private UUID companyDeliveryAgentId; // 업체배송담당자
    private UUID hubTotalRouteId;
    private UUID fromHubId;
    private UUID toHubId;
    private UUID receiverCompanyId; // 수령업체 id
    private UUID supplierCompanyId; // 공급업체 id

}
