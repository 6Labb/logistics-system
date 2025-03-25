package com.sixlab.logistics.order_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HubResponseDto {
    private UUID id;
    private String hubName;
    private String hubAddress;
    private double latitude;
    private double longitude;
    private Long hubManagerUserId;
}
