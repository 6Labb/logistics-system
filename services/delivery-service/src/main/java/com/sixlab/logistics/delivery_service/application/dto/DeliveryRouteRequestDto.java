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

    private Integer sequence;
    private Double esimateDistance;
    private Integer esimateTime;
    private DeliveryRouteStatus status;
    private UUID fromHubId;
    private UUID toHubId;
}
