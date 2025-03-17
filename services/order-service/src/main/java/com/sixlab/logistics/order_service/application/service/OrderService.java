package com.sixlab.logistics.order_service.application.service;

import com.sixlab.logistics.common.exception.ResourceNotFoundException;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderCreateResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    private final UUID productId = UUID.fromString("50c3068a-6b09-4f45-a3af-c119168a7676");


    public OrderCreateResponseDto createOrder(OrderCreateRequestDto dto) throws Exception{
        log.info("service 계층: createOrder() 호출됨");
        // 1. 모든 로그인 사용자 (주문자) 가능 --> 로그인 여부는 필터에서 걸러지고
        // & userId 가 user 테이블에 존재하는지 확인 (토큰으로)

        // 2. 상품 테이블에서 상품이 존재하는지
        if(!dto.getProductId().equals(productId)) {
            log.info("상품이 존재하지 않을 경우 이 로그가 찍힌다.");
            throw new ResourceNotFoundException("존재하지 않는 상품입니다.");
        }

        // (상품 id 기반으로 객체를 전달받은상태) & 요청 상품 수량이 재고 수량보다 적거나 같은지 확인



        // 3. 배송 마이크로 서비스 호출 --> 배송 id 를 전달받고

        // 4. Order 엔터티 객체 생성후 데이터베이스에 저장한다.

        return null;
    }
}
