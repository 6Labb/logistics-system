package com.sixlab.logistics.hub_service.hub.infrastructure.feign;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ExternalUserResponseDto {

    private Long id;

    private String slackId;

//    @Builder
//    private ExternalUserResponseDto(Long id, String slackId) {
//        this.id = id;
//        this.slackId = slackId;
//    }
//
//    public static ExternalUserResponseDto of(HubManager hubManager) {
//        return ExternalUserResponseDto.builder()
//                .id(hubManager.getUserId())
//                .slackId(hubManager.getSlackId())
//                .build();
//    }
}
