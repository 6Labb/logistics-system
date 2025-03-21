package com.sixlab.logistics.hub_service.hubmanager.application.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class HubManagerUpdateRequestDto {

    private UUID hubId;

}
