//package com.sixlab.logistics.order_service.application.service;
//
//import com.sixlab.logistics.order_service.OrderServiceApplication;
//import com.sixlab.logistics.order_service.application.dto.request.OrderInfoUpdateRequestDto;
//import com.sixlab.logistics.order_service.application.dto.response.OrderDeleteResponseDto;
//import com.sixlab.logistics.order_service.application.dto.response.OrderFindOneResponseDto;
//import com.sixlab.logistics.order_service.application.dto.response.OrderInfoUpdateResponseDto;
//import com.sixlab.logistics.order_service.config.OrderApplicationQueueConfig;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//import java.util.UUID;
//
//@SpringBootTest(classes = OrderServiceApplication.class)
//@Import(OrderApplicationQueueConfig.class)
//class OrderServiceTest {
//    private final UUID productId = UUID.fromString("50c3068a-6b09-4f45-a3af-c119168a7676");
//    private final UUID receiverCompanyId = UUID.fromString("c0502b76-1beb-4d6d-a2d3-3f61ca1b7574"); // 수령업체 id
//
//    @Autowired
//    private OrderService orderService;
//
//    private Logger log = LoggerFactory.getLogger("order-service-test");
//
////    @Test
////    @DisplayName("주문생성이 되어야 한다.")
////    void test() {
////        // given
////
////        // 클라이언트로부터 전달받은 데이터(커맨드 객체로 래핑)
////        OrderCreateRequestDto data = new OrderCreateRequestDto();
////        data.setReceiverName("홍길동");
////        data.setMessage("2025년 6월 1일까지 보내주세요.");
////        data.setAddress("서울특별시 고구마구 고구마동 111번지");
////        data.setQuantity(5);
////        data.setProductId(productId);
////        data.setReceiverId(receiverCompanyId);
////
////        // when
////        try{
////            OrderCreateResponseDto order = orderService.createOrder(data, userDetails);
////            System.out.println("생성된 주문: "+order);
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////        // then
////    }
//
//    @Test
//    @DisplayName("단건 주문조회가 되어야 한다.")
//    void test2() {
//        // given
//        UUID orderId = UUID.fromString("20f0290b-9143-40cf-9093-169f7d9f7f90");
//
//        // when
//        OrderFindOneResponseDto oneOrder = orderService.findOneOrder(orderId, userDetails.getUserInfo());
//        log.info(oneOrder.toString());
//
//        /* 로그 찍히는 것 확인
//            * OrderFindOneResponseDto(orderId=24938764-7944-4f95-9774-5c8d6335b256, supplierId=edb45825-cafb-457f-9179-7d544b1ec78a, receiverId=c0502b76-1beb-4d6d-a2d3-3f61ca1b7574, productId=50c3068a-6b09-4f45-a3af-c119168a7676, quantity=10, message=2025년 6월 1일까지 보내주세요., deliveryId=50c6789a-6b09-4f45-a3af-c119168a7676, status=SUCCESS, userId=da66e6a7-a123-4b34-8724-7dd9080fa928, receiverName=홍길동)
//        * */
//        // then
//
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("주문정보가 수정되어야 한다.")
//    void test3() {
//        // given
//        UUID orderId = UUID.fromString("20f0290b-9143-40cf-9093-169f7d9f7f90");
//        OrderInfoUpdateRequestDto data = new OrderInfoUpdateRequestDto();
//        data.setMessage("수정되는지 볼까?");
//        data.setQuantity(5);
//
//        // when
//        OrderInfoUpdateResponseDto orderInfoUpdateResponseDto = orderService.orderInfoUpdate(orderId, data, userDetails);
//        log.info(orderInfoUpdateResponseDto.toString());
//
//        /* 로그 찍히는 것 확인
//         * */
//        // then
//
//    }
//
//    @Test
//    @DisplayName("주문정보가 삭제되어야 한다.")
//    @Rollback(value = false)
//    void test4() {
//        // given
//        UUID orderId = UUID.fromString("20f0290b-9143-40cf-9093-169f7d9f7f90");
//        // when
//        OrderDeleteResponseDto dto = orderService.deleteOrder(orderId);
//        log.info(dto.toString());
//
//        /* 로그 찍히는 것 확인
//         * */
//        // then
//
//    }
//
//    @Test
//    @DisplayName("2행이 조회되어야 한다.")
//    @Rollback(value = false)
//    void test5() {
//        // given
//        UserInfo user = new UserInfo(1L, UserInfo.Role.MASTER);
//        // when
//        List<OrderFindOneResponseDto> orderList = orderService.getOrderListByRole(user);
//
//        // then
//        Assertions.assertThat(orderList.size()).isEqualTo(2);
//
//    }
//
//
//
//}