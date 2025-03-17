package com.sixlab.logistics.order_service.infrastructure.persistence;

import com.sixlab.logistics.order_service.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
}
