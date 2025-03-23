package com.sixlab.logistics.product_service.application.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixlab.logistics.product_service.application.client.CompanyClient;
import com.sixlab.logistics.product_service.application.dto.CompanyResponseDto;
import com.sixlab.logistics.product_service.domain.model.Product;
import com.sixlab.logistics.product_service.domain.model.QProduct;
import com.sixlab.logistics.product_service.domain.repository.ProductRepository;
import com.sixlab.logistics.product_service.presentaion.dto.PaginationResponseDto;
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
    private final JPAQueryFactory queryFactory;
    private final CompanyClient companyClient;

    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.toEntity(requestDto);
        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.fromEntity(savedProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponseDto<ProductResponseDto> getAllProducts(int page, int size, String name) {
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        // 이름으로만 검색
        if (name != null) {
            builder.and(product.name.containsIgnoreCase(name));
        }

        JPQLQuery<Product> query = queryFactory.selectFrom(product)
                .where(builder)
                .offset(page * size)
                .limit(size);

        // 상품 리스트 가져오기
        List<ProductResponseDto> products = query.fetch().stream()
                .map(p -> {
                    // 상품 정보 조회 후 companyId와 hubId 추가
                    ProductResponseDto productResponseDto = ProductResponseDto.fromEntity(p);

                    // CompanyClient를 통해 companyId와 hubId 조회
                    UUID companyId = p.getCompanyId();  // companyId가 UUID 타입이라고 가정
                    CompanyResponseDto company = companyClient.getCompanyById(companyId.toString());  // CompanyClient에서 UUID로 처리하려면 String으로 변환

                    // CompanyResponseDto에서 hubId를 가져옴
                    UUID hubId = company.getHubId();  // CompanyResponseDto에 hubId가 있다고 가정

                    // ProductResponseDto에 companyId, hubId 설정
                    productResponseDto.setCompanyId(companyId);  // companyId는 UUID로 설정
                    productResponseDto.setHubId(hubId);

                    return productResponseDto;
                })
                .collect(Collectors.toList());

        long totalItems = query.fetchCount();  // 총 아이템 수
        int totalPages = (int) Math.ceil((double) totalItems / size);  // 총 페이지 수

        return new PaginationResponseDto<>(products, page, size, totalItems, totalPages);
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
