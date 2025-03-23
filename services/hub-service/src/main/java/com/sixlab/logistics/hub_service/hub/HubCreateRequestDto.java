package com.sixlab.logistics.hub_service.hub;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubCreateRequestDto {

    private String hubName;
    private String hubAddress;
    private double latitude;
    private double longitude;
}
