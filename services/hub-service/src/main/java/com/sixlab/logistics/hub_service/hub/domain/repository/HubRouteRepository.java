package com.sixlab.logistics.hub_service.hub.domain.repository;

import com.sixlab.logistics.hub_service.hub.application.dto.hubroute.HubRouteRequestDto;
import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import com.sixlab.logistics.hub_service.hub.domain.model.HubRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRouteRepository extends JpaRepository<HubRoute, UUID> {

//    List<HubRoute> findAllByDeletedAtIsNull(UUID id);
//    void update(HubRouteRequestDto requestDto);

    Optional<HubRoute> findByIdAndDeletedAtIsNull(UUID hubRouteId);

    Optional<HubRoute> findByDepartureHubAndArrivalHub(Hub departureHub, Hub arrivalHub);

}
