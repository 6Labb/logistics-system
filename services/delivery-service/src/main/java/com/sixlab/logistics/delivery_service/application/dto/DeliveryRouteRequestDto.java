package com.sixlab.logistics.delivery_service.application.dto;

import com.sixlab.logistics.delivery_service.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryRouteStatus;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRouteRequestDto {

    private UUID id;
    private Integer sequence;
    private Double esimateDistance;
    private Integer esimateTime;
    private Double actualDistance;
    private Integer actualTime;
    private DeliveryRouteStatus status;
    private UUID deliveryId;
    private UUID fromHubId;
    private UUID toHubId;
    private Long deliveryAgentId; // 마지막 배송경로에는 업체배송담당자가 들어감
}
