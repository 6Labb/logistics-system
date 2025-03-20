package com.sixlab.logistics.product_service.application.service;

import com.sixlab.logistics.product_service.presentaion.dto.ProductRequestDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(UUID productId);
    ProductResponseDto updateProduct(UUID productId, ProductRequestDto requestDto);
    void deleteProduct(UUID productId);
}
