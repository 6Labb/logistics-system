package com.sixlab.logistics.hub_service.hubmanager.domain.repository;

import com.sixlab.logistics.hub_service.hubmanager.domain.model.HubManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HubManagerRepository extends JpaRepository<HubManager, UUID> {

    List<HubManager> findAllByDeletedAtIsNull();

}
