package com.sixlab.logistics.hub_service.hub.domain.repository;

import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface HubRouteRepository extends JpaRepository<HubRoute, UUID> {


}
