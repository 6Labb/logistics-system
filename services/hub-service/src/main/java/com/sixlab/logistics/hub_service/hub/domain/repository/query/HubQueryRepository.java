package com.sixlab.logistics.hub_service.hub.domain.repository.query;

import com.sixlab.logistics.hub_service.hub.domain.model.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubQueryRepository {

    Page<Hub> searchHubs(String keyword, String sort, String order, Pageable pageable);

}
