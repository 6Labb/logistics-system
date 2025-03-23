package com.sixlab.logistics.hub_service.hub.domain.repository;

import com.sixlab.logistics.hub_service.hub.domain.model.HubManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubManagerRepository extends JpaRepository<HubManager, UUID>, JpaSpecificationExecutor<HubManager> {

    List<HubManager> findAllByDeletedAtIsNull();

    Optional<HubManager> findByUserId(Long hubManagerUserId);

}
