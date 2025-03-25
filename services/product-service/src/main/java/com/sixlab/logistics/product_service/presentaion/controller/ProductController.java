package com.sixlab.logistics.product_service.presentaion.controller;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.product_service.application.service.ProductService;
import com.sixlab.logistics.product_service.domain.model.Product;
import com.sixlab.logistics.product_service.presentaion.dto.PaginationResponseDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductRequestDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductResponseDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductStockResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 등록
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','TRADE_PARTNER')")
    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(
            @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ApiResponse.success(HttpStatus.CREATED, responseDto, "상품이 정상적으로 등록되었습니다.");
    }

    // 상품 목록 조회
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','TRADE_PARTNER','DELIVERY_AGENT')")
    @GetMapping
    public ApiResponse<PaginationResponseDto<ProductResponseDto>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name
    ) {
        PaginationResponseDto<ProductResponseDto> products = productService.getAllProducts(page, size, name);
        return ApiResponse.success(products, "상품 목록 조회 성공");
    }

    // 상품 단건 조회
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','TRADE_PARTNER','DELIVERY_AGENT')")
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponseDto> getProductById(
            @PathVariable UUID productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ApiResponse.success(product, "상품 조회 성공");
    }

    // 상품 수정
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','TRADE_PARTNER')")
    @PutMapping("/{productId}")
    public ApiResponse<ProductResponseDto> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto updatedProduct = productService.updateProduct(productId, requestDto);
        return ApiResponse.success(updatedProduct, "상품 수정 성공");
    }

    // 상품 삭제
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER')")
    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(
            @PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ApiResponse.success(HttpStatus.OK, null, "상품이 정상적으로 삭제되었습니다.");
    }

    // 상품 재고 감소
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','TRADE_PARTNER','DELIVERY_AGENT')")
    @PutMapping("/{productId}/decrease-stock")
    public ApiResponse<ProductStockResponseDto> decreaseStock(
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        ProductStockResponseDto responseDto = productService.decreaseStock(productId, quantity);
        return ApiResponse.success(HttpStatus.OK, responseDto, "상품 재고 감소 성공");
    }

    // 상품 재고 복원
    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER','TRADE_PARTNER','DELIVERY_AGENT')")
    @PutMapping("/{productId}/restore-stock")
    public ApiResponse<ProductStockResponseDto> restoreStock(
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        ProductStockResponseDto responseDto = productService.restoreStock(productId, quantity);
        return ApiResponse.success(HttpStatus.OK, responseDto, "상품 재고 복원 성공");
    }
}
