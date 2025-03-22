package com.sixlab.logistics.hub_service.hub.domain.repository;

import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubManagerRepository extends JpaRepository<HubManager, UUID> {

    List<HubManager> findAllByDeletedAtIsNull();

    Optional<Object> findByUserId(Long hubManagerUserId);
}
