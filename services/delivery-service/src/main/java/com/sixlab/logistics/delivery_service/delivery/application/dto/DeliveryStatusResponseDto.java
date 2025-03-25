package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryStatusResponseDto {

    private DeliveryStatus status;

    public DeliveryStatusResponseDto(DeliveryStatus status) {
        this.status = status;
    }
}
