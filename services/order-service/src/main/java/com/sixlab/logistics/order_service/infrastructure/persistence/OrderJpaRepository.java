package com.sixlab.logistics.order_service.infrastructure.persistence;

import com.sixlab.logistics.order_service.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    List<Order> findAllByUserId(Long userId);
}