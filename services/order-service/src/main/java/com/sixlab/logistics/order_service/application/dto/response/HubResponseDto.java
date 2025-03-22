package com.sixlab.logistics.order_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HubResponseDto {
    private String hubName;
    private String hubAddress;
    private double latitude;
    private double longitude;
    private Long hubManagerId;
}
