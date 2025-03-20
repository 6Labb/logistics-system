package com.sixlab.logistics.product_service.application.service;

import com.sixlab.logistics.product_service.domain.model.Product;
import com.sixlab.logistics.product_service.domain.repository.ProductRepository;
import com.sixlab.logistics.product_service.presentaion.dto.ProductRequestDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductResponseDto;
import com.sixlab.logistics.product_service.presentaion.dto.ProductStockResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.toEntity(requestDto);
        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.fromEntity(savedProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponseDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));
        return ProductResponseDto.fromEntity(product);
    }

    @Transactional
    @Override
    public ProductResponseDto updateProduct(UUID productId, ProductRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

        product.updateProduct(requestDto);
        return ProductResponseDto.fromEntity(product);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("해당 상품을 찾을 수 없습니다.");
        }
        productRepository.deleteById(productId);
    }

    @Transactional
    @Override
    public ProductStockResponseDto decreaseStock(UUID productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        product.decreaseStock(quantity);  // 재고 감소
        productRepository.save(product);

        return ProductStockResponseDto.fromEntity(product);  // 변경된 상품 정보 반환
    }

    @Transactional
    @Override
    public ProductStockResponseDto restoreStock(UUID productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        product.increaseStock(quantity);  // 재고 복원
        productRepository.save(product);

        return ProductStockResponseDto.fromEntity(product);  // 변경된 상품 정보 반환
    }
}
