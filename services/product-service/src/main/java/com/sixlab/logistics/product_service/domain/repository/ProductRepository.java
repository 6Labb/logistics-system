package com.sixlab.logistics.product_service.domain.repository;

import com.sixlab.logistics.product_service.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
