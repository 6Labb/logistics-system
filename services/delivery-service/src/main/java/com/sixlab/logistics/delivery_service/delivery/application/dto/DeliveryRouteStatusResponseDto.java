package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryRouteStatus;
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
