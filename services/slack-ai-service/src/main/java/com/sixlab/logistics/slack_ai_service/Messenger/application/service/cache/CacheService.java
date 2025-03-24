package com.sixlab.logistics.slack_ai_service.Messenger.application.service.cache;

import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.common.shared.response.ApiResponseHelper;
import com.sixlab.logistics.slack_ai_service.Messenger.application.dto.ai.HubClientResponseDto;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.HubClient;
import com.sixlab.logistics.slack_ai_service.Messenger.infrastructure.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CacheService {
    private final HubClient hubClient;
    private final UserClient userClient;

    @Cacheable(value = "hubName", key = "#hubId")
    public String getHubName(UUID hubId) {
        ApiResponse<HubClientResponseDto> hubResponse = hubClient.getHub(hubId);
        HubClientResponseDto hubData = ApiResponseHelper.extractData(hubResponse, Function.identity());
        return hubData.getHubName();
    }
//    //유저 정보도 캐싱가능한데.. 흠 ..
//    @Cacheable(value = "userName",key = "#userId")
//    public String getUserName(UUID userId) {
//
//    }
}
