package com.sixlab.logistics.delivery_service.delivery.application.dto;

import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusRequestDto {

    private DeliveryStatus status;

}
