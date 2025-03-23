package com.sixlab.logistics.hub_service.hub.application.dto.hubmanager;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HubManagerSearchCondition {

    private UUID id;
    private Long UserId;
    private UUID hubId;

}
