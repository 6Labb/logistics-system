package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusRequestDto {

    private DeliveryStatus status;

}
