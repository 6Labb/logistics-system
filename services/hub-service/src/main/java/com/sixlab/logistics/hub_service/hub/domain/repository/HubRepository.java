package com.sixlab.logistics.hub_service.hub.domain.repository;


import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID> {
    List<Hub> findAllByDeletedAtIsNull(); // 삭제되지 않은 데이터만 조회
}
