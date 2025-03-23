package com.sixlab.logistics.hub_service.hub.application.dto.hubmanager;

import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class HubManagerSpecification {

    public static Specification<HubManager> userIdEq(Long userId) {
        return (root, query, cb) ->
                userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    public static Specification<HubManager> hubIdEq(UUID hubId) {
        return (root, query, cb) ->
                hubId == null ? null : cb.equal(root.get("hubId"), hubId);
    }

//    public static Specification<HubManager> createdAfter(LocalDateTime time) {
//        return (root, query, cb) ->
//                time == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), time);
//    }
}
