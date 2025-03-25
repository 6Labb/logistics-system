package com.sixlab.logistics.order_service.application.client;

import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;

import com.sixlab.logistics.order_service.application.dto.response.ProductStockResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name="product-service")
// @RequestMapping("/products") 여기에 @RequestMapping 어노테이션 사용해도 되나?
public interface ProductClient {
    // product 조회 (param 은 productId)
    @GetMapping("/products/{productId}")
    ResponseEntity<ApiResponseDto<GetProductResponseDto>> getProductById(@PathVariable UUID productId);

    // 상품 수량 감소 요청
    @PutMapping("products/{productId}/decrease-stock")
    ResponseEntity<ApiResponseDto<ProductStockResponseDto>> requestProductStockDecrease(@PathVariable UUID productId,
                                                                                        @RequestParam int quantity);

    // 상품 수량 복원 요청
    @PutMapping("products/{productId}/restore-stock")
    ResponseEntity<ApiResponseDto<ProductStockResponseDto>> requestProductStockRestore(@PathVariable UUID productId,
                                                                                        @RequestParam int quantity);




}
