package com.sixlab.logistics.order_service.application.service;

import com.sixlab.logistics.order_service.OrderServiceApplication;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderCreateResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = OrderServiceApplication.class)
class OrderServiceTest {
    private final UUID productId = UUID.fromString("50c3068a-6b09-4f45-a3af-c119168a7676");
    private final UUID receiverCompanyId = UUID.fromString("c0502b76-1beb-4d6d-a2d3-3f61ca1b7574"); // 수령업체 id

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문생성이 되어야 한다.")
    void test() {
        // given

        // 클라이언트로부터 전달받은 데이터(커맨드 객체로 래핑)
        OrderCreateRequestDto data = new OrderCreateRequestDto();
        data.setReceiverName("홍길동");
        data.setMessage("2025년 6월 1일까지 보내주세요.");
        data.setAddress("서울특별시 감자구 감자동 111번지");
        data.setQuantity(10);
        data.setProductId(productId);
        data.setReceiverId(receiverCompanyId);

        // when
        try{
            OrderCreateResponseDto order = orderService.createOrder(data);
            System.out.println("생성된 주문: "+order);
        }catch (Exception e){
            e.printStackTrace();
        }
        // then
    }

}