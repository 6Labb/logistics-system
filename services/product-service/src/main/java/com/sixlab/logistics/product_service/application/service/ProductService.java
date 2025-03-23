package com.sixlab.logistics.product_service.application.service;

import com.sixlab.logistics.product_service.presentaion.dto.PaginationResponseDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductRequestDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);
    PaginationResponseDto<ProductResponseDto> getAllProducts(int page, int size, String name);
    ProductResponseDto getProductById(UUID productId);
    ProductResponseDto updateProduct(UUID productId, ProductRequestDto requestDto);
    void deleteProduct(UUID productId);
}
