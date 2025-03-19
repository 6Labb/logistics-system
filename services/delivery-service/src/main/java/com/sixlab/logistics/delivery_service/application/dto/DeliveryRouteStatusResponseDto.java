package com.sixlab.logistics.delivery_service.application.dto;

import com.sixlab.logistics.delivery_service.domain.entity.DeliveryRouteStatus;
import com.sixlab.logistics.delivery_service.domain.entity.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryRouteStatusResponseDto {

    private DeliveryRouteStatus status;

    public DeliveryRouteStatusResponseDto(DeliveryRouteStatus status) {
        this.status = status;
    }
}
