package com.sixlab.logistics.order_service.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixlab.logistics.common.shared.exception.OutOfStockException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.response.ApiResponseDto;
import com.sixlab.logistics.order_service.application.client.CompanyClient;
import com.sixlab.logistics.order_service.application.client.DeliveryClient;
import com.sixlab.logistics.order_service.application.client.ProductClient;
import com.sixlab.logistics.order_service.application.dto.UserInfo;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;

import com.sixlab.logistics.order_service.application.dto.request.OrderInfoMessageRequestDto;
import com.sixlab.logistics.order_service.application.dto.request.OrderInfoUpdateRequestDto;
import com.sixlab.logistics.order_service.application.dto.request.RequestDeliveryRegisterDto;
import com.sixlab.logistics.order_service.application.dto.response.*;
import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.infrastructure.persistence.OrderJpaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.*;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final ObjectMapper objectMapper;

    private final UUID productId = UUID.fromString("50c3068a-6b09-4f45-a3af-c119168a7676");
    private final UUID companyId = UUID.fromString("edb45825-cafb-457f-9179-7d544b1ec78a"); // 공급업체 id
    private final UUID receiverCompanyId = UUID.fromString("c0502b76-1beb-4d6d-a2d3-3f61ca1b7574"); // 수령업체 id
    // 허브 id: GetProductResponseDto 에 상품 id, 공급업체 id, 수량 등의 필드 정보가 존재하고,
    // 해당 허브 id 는 상품 id 를 관리하고 있으며 해당 상품의 수량은 quantity 필드를 참고하면 된다.
    private final UUID hubId = UUID.fromString("69199641-5072-4036-a899-af524390fb93");
    private final Long userId = 1L;
    private final UUID deliveryId = UUID.fromString("50c6789a-6b09-4f45-a3af-c119168a7676");
    private final UUID deliveryAgentId = UUID.fromString("50c7777a-6b09-4f45-a3af-c119168a7676");

    private final DeliveryClient deliveryClient;
    private final ProductClient productClient;
    private final CompanyClient companyClient;
    // private final HubClient hubClient;

    private final OrderJpaRepository orderJpaRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.order}")
    private String queueOrder;

    public OrderCreateResponseDto createOrder(OrderCreateRequestDto dto) throws Exception{
        log.info("service 계층: createOrder() 호출됨");
        // 1. 모든 로그인 사용자 (주문자) 가능
        // jwt 를 통해 user 의 id 를 얻어낸다.

        // 2. 상품서비스의 상품 조회기능 호출
        ResponseEntity<ApiResponseDto<GetProductResponseDto>> requestProduct = productClient.getProductById(dto.getProductId());

        /* 상기코드 실행시 전달받을 객체, 지금은 Product 객체 직접 생성
        GetProductResponseDto getProduct = GetProductResponseDto.builder()
                .id(productId) // 50c3068a-6b09-4f45-a3af-c119168a7676
                .companyId(companyId)
                .hubId(hubId)
                .name("지우개")
                .quantity(40)
                // .createdBy(40) // 이 상품을 생성한 사용자
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .build(); */

        ApiResponseDto<?> apiResponseDto = checkFeignClientResponse(requestProduct);
        GetProductResponseDto getProduct = (GetProductResponseDto)apiResponseDto.getData();
        ifExist(getProduct);

        // (상품 id 기반으로 객체를 전달받은상태) & 요청 상품 수량이 재고 수량보다 적거나 같은지 확인
        // 상품 객체에 허브 id, 공급업체 id, (재고) 수량 필드 등이 존재하고,
        // 여기서 (재고) 수량은 허브 id 가 관리하는 수량으로 정의함 (팀원들끼리 합의봄)
        // *** 클라이언트로부터 요청수량 0 미만으로는 받지 못하도록 설정 @Min(value=1)
        /*
        if(dto.getQuantity() > getProduct.getQuantity()) {
            log.info("요청 수량이 재고 수량보다 많음");
            log.info("요청 수량: {}, 재고 수량: {}", dto.getQuantity(), getProduct.getQuantity());
            String data = "요청 수량이 재고 수량을 초과했습니다. 최대 주문 가능 수량을 확인해 주세요.\n 최대 주문 가능 수량: "+getProduct.getQuantity();
            throw new OutOfStockException(data);
        }*/
        checkProductStock(dto.getQuantity(), getProduct.getQuantity());

        // 3. 재고 감소 요청하기
        ResponseEntity<ApiResponseDto<ProductStockResponseDto>> stockDecreaseResponse = productClient.requestProductStockDecrease(dto.getProductId(), dto.getQuantity());

        // stockDecreaseResponse.getStatusCode() --> HttpStatus 객체
        // 상태코드가 2 로 시작하지 않을 경우 서비스 호출에 실패
        if(!stockDecreaseResponse.getStatusCode().is2xxSuccessful()) {
            log.error("재고 감소 요청 실패 - 상태코드: {}, 데이터: {}", stockDecreaseResponse.getStatusCode(), stockDecreaseResponse.getBody());
            // stockDecreaseResponse.getBody() --> ApiResponseDto<ProductStockResponseDto>
            throw new Exception("재고 감소 요청이 실패했습니다. 상태 코드: " + stockDecreaseResponse.getStatusCode());
        }

        // 4. company-service 의 조회 메서드 호출하기
        // 클라이언트로부터 전달받은 수령업체가 존재하는지 확인
        ResponseEntity<ApiResponseDto<GetCompanyResponseDto>> response = companyClient.getCompanyById(dto.getReceiverId());

        // http 상태 코드가 2 로 시작하고, response.getBody() 가 null 이 아님을 확인
        ApiResponseDto<GetCompanyResponseDto> getBody = checkFeignClientResponse(response);
        GetCompanyResponseDto getReceiverCompanyInfo = getBody.getData();

        /* 상기 코드 호출시 하기의 객체를 전달받음
        GetCompanyResponseDto getCompanyInfo = GetCompanyResponseDto.builder()
                .type(GetCompanyResponseDto.CompanyType.RECEIVER)
                .name("스파르타")
                // 업체 정보 생성한 일자 정보
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .hubId(hubId) // 업체 테이블에 왜 hub id 가??
                .id(receiverCompanyId) // 수령업체 id 여야함. 지금 이 객체에서는
                .build();*/
        if(getReceiverCompanyInfo == null || getReceiverCompanyInfo.getType() != GetCompanyResponseDto.CompanyType.RECEIVER) {
            // company-service 의 조회 메서드 호출 후 전달받은 company 객체가 null 이라면 또는
            // null 이 아닌데 객체에서 업체 타입의 정보가 RECEIVER 가 아니라면
            throw new ResourceNotFoundException("수령업체 정보가 존재하지 않습니다.");
        }

        // 4. 배송등록 마이크로 서비스 호출에 전달할 데이터 생성
        RequestDeliveryRegisterDto requestDeliveryRegisterDto = RequestDeliveryRegisterDto.builder()
                .supplierCompanyId(getProduct.getCompanyId()) // 공급업체 id
                .receiverCompanyId(getReceiverCompanyInfo.getId()) // 수령업체 id
                .deliveryAddress(dto.getAddress()) // 배송지
                .receiveName(dto.getReceiverName()) // 수령인
                .build();

        // 하기와 같은 코드로 배송 서비스 호출 --> 배송 서비스의 배송등록 메서드 호출됨
        ResponseEntity<ApiResponseDto<ResponseDeliveryRegisterDto>> delieveryResponse = deliveryClient.requestDeliveryRegister(requestDeliveryRegisterDto);

        if(!delieveryResponse.getStatusCode().is2xxSuccessful()) {
            log.info("배송 등록 요청했고, ResponseEntity 의 상태코드 문제");
            log.error("배송 서비스 요청 실패 - 상태코드: {}, 데이터: {}", delieveryResponse.getStatusCode(), delieveryResponse.getBody());
            throw new Exception("배송 서비스 요청이 실패했습니다. 상태 코드: " + delieveryResponse.getStatusCode());
        }

        ResponseDeliveryRegisterDto getDelivery = delieveryResponse.getBody().getData();

        // 하기는 배송 서비스로부터 ResponseEntity 가
        // 반환됐지만 data 가 없을 때 예외 처리
        if(getDelivery == null) {
            log.info("배송 서비스 호출 --> 배송등록 메서드 호출 --> 실패");
            throw new ResourceNotFoundException("배송등록 서비스 호출에 실패하였습니다.");
        }

        // 5. Order 엔터티 객체 만들어서 저장하기
        Order order = dto.toEntity(getProduct.getCompanyId(), userId, getDelivery.getId());
        Order savedOrder = orderJpaRepository.save(order);

        //AI 호출용 RabbitMQ
        OrderInfoMessageRequestDto message = createOrderMessage(savedOrder.getOrderId(),getProduct, dto, getDelivery);
        rabbitTemplate.convertAndSend(exchange, "order.created", message);
        System.out.println("📤 주문 메시지 전송됨: " +exchange+" : "+ queueOrder+" : "+message); //테스트후  삭제

        return new OrderCreateResponseDto(savedOrder);
    }

    // FeignClient 호출시 반환 값 검증: 메서드 호출에 문제가 없었는지에 대한 확인 절차
    private static <T> ApiResponseDto<T> checkFeignClientResponse(ResponseEntity<ApiResponseDto<T>> response) {
        if(!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().getStatus() != HttpStatus.OK.value()) {
            throw new RuntimeException("FeignClient 호출 문제 발생 - http 상태 코드: "+ response.getStatusCode());
        }
        return response.getBody();
    }

    // 객체를 전달했을 때 비어있는지의 여부를 확인하는 메서드
    // - 어떤 객체와 예외를 매개변수로 받고
    // - 객체가 null 인지 비교,
    // - null 이라면 함께 전달받은 예외를 발생시키는 로직으로 develop 하기

    // 제네릭을 사용해서 다양한 타입의 객체를 받아 객체의 null 의 여부를 검증하는 메서드
    // 무치형 타입: 값을 반환하지 않는
    private <T> void ifExist(T data) {
        if(data == null) {
            log.info("상품이 존재하지 않을 경우 이 로그가 찍힌다.");
            throw new ResourceNotFoundException("존재하지 않는 상품입니다.");
        }
    }

    // 권한확인 x
    // 주문 단건 조회 서비스
    public OrderFindOneResponseDto findOneOrder(UUID orderId) {
        return new OrderFindOneResponseDto(findByIdOneOrderInfo(orderId));
    }

    @Transactional
    // 마스터와 허브 매니저만 호출 가능한 수정 메서드
    public OrderInfoUpdateResponseDto orderInfoUpdate(UUID orderId, OrderInfoUpdateRequestDto dto) {
        // 1. 주문정보가 존재하는지 먼저 확인
        Order order = findByIdOneOrderInfo(orderId);

        // 2. 기존 주문했던 물품 요청 수량과 수정 요청 수량이 다르다면
        log.info("기존 물품 요청 수량: {}, 수정 요청 수량: {}", order.getQuantity(), dto.getQuantity());
        if(!order.getQuantity().equals(dto.getQuantity()))
        {
            // 3. productId 를 기반으로 상품 서비스 조회 호출
            ResponseEntity<ApiResponseDto<GetProductResponseDto>> response = productClient.getProductById(order.getProductId());
            GetProductResponseDto getProduct = response.getBody().getData();
            /* 상기 상품 서비스 호출시 전달받을 물품 객체
            GetProductResponseDto getProduct = GetProductResponseDto.builder()
                    .id(order.getProductId())// .id(productId) // 50c3068a-6b09-4f45-a3af-c119168a7676
                    .companyId(companyId)
                    .hubId(hubId)
                    .name("지우개")
                    .quantity(40)
                    // .createdBy(40) // 이 상품을 생성한 사용자
                    .createdAt(LocalDateTime.now().minusWeeks(1))
                    .build();*/
            /* ------------------------------------------------------------
            if(getProduct == null) {
                log.info("상품이 존재하지 않을 경우 이 로그가 찍힌다.");
                throw new ResourceNotFoundException("존재하지 않는 상품입니다.");
            }*/
            // getProduct 가 null 이라면 예외 발생
            ifExist(getProduct);

            // 요청수량이 상품 재고 수량보다 많으면 예외 발생
            checkProductStock(dto.getQuantity(), getProduct.getQuantity());

            order.setQuantity(dto.getQuantity());
        }

        log.info("기존 요청 메시지: {}, 수정 요청 메시지: {}", order.getMessage(), dto.getMessage());
        if(!order.getMessage().trim().equals(dto.getMessage().trim())) order.setMessage(dto.getMessage());
        Order updatedOneOrder = orderJpaRepository.save(order);
        return new OrderInfoUpdateResponseDto(updatedOneOrder);
    }

    private void checkProductStock(Integer requestQuantity, Integer existQuantity) {
        if(requestQuantity > existQuantity) {
            log.info("요청 수량이 재고 수량보다 많음");
            log.info("요청 수량: {}, 재고 수량: {}", requestQuantity, existQuantity);
            String data = "요청 수량이 재고 수량을 초과했습니다. 최대 주문 가능 수량을 확인해 주세요.\n 최대 주문 가능 수량: "+ existQuantity;
            throw new OutOfStockException(data);
        }
    }

    // orderId 에 기반하여 주문정보 확인하는 메서드 -> 주문정보 존재한다면 Order 객체를 반환
    private Order findByIdOneOrderInfo(UUID orderId) {
        return orderJpaRepository.findById(orderId).orElseThrow(() -> {
            log.info("주문정보가 없음");
            return new ResourceNotFoundException("주문 정보를 찾을 수 없습니다.");
        });
    }

    // 주문 삭제 메서드, 마스터와 담당! 허브 관리자만이 삭제를 할 수 있다.
    @Transactional
    public OrderDeleteResponseDto deleteOrder(UUID orderId) {
        // 1. 주문정보 확인(주문 id)
        Order order = findByIdOneOrderInfo(orderId);

        // 2. 권한 확인
        // 허브관리자라면 담당 허브 관리자인지 확인
        // order 객체에서 productId 를 얻고,
        // 1) product 서비스 호출: productId 를 기반으로 product 객체를 얻고,
        // 2) hub 서비스 호출: product 객체에서 얻은 hubId 를 기반으로 hub 객체를 얻고,
        // --> 그리고 비교해서 맞지 않다면 접근 권한이 없다고 리턴한다.

        // 3. 상품 서비스에게 수량 만큼의 복원을 요청한다.
        ResponseEntity<ApiResponseDto<ProductStockResponseDto>> response =
                productClient.requestProductStockRestore(order.getProductId(), order.getQuantity());

        if(!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().getStatus() != HttpStatus.OK.value()) {
            // http 상태 코드와 api response 형식에 담긴 status 의 값을 확인해보자.
            log.info("response.getStatus(): {}", response.getStatusCode());

            // 에러코드가 500, 400 등이라면 응답 본문이 없을 수 있으니(response.getBody() == null)
            // 아래 로그에서 getStatus() 가 안찍힐 가능성이 있음.
            log.info("response.getBody() - ApiResponse<ProductStockResponseDto> 의 status: {}", response.getBody().getStatus());
            log.info("message: {}", response.getBody().getMessage()); // 에러메시지가 반환될 것임.

            String errorMessage = "Error response from ProductService. HTTP Status: " + response.getStatusCode() + ", " +"API Response Status: " + response.getBody().getStatus();

            throw new FeignException.FeignClientException(response.getStatusCode().value(),
                    errorMessage, null, null, null);
        }

        // 4. 배송 서비스에게 배송 id 삭제를 요청한다.
        // {"message": "SUCCESS", "data":null}
        // deliveryClient.requestDeliveryDelete(order.getDeliveryId());

        // 5. 주문을 삭제한다(소프트 삭제)
        order.delete(userId);
        return new OrderDeleteResponseDto(order);
    }

    // --------------------------------------------------------------
    public List<OrderFindOneResponseDto> getOrderListByRole(UserInfo user) {
            switch(user.getRole()) {
                case MASTER:
                    return transDtoList(orderJpaRepository.findAll());
                // 1. 허브 관리자는 담당 허브 조회만 가능하다는데 이걸 어떤 로직으로 풀어나가야할지...?
                // case HUB_MANAGER:
                case DELIVERY_AGENT:
                case TRADE_PARTNER:
            }    // 허브 매니저 로직을 추가하기 전이라 허브 매니저도 하기와 같은 코드가 작동됨.
        return transDtoList(orderJpaRepository.findAllByUserId(user.getUserId())
        );
    }

    private List<OrderFindOneResponseDto> transDtoList(List<Order> orderList) {
        List<OrderFindOneResponseDto> dtoList = new ArrayList<>();
        if(!orderList.isEmpty()) {
            for(Order order : orderList) {
                dtoList.add(new OrderFindOneResponseDto(order));
            }
        }
        // 데이터베이스로부터 반환된 행이 0 이라면 비어있는 리스트가 반환됨.
        return dtoList;
    }

    private OrderInfoMessageRequestDto createOrderMessage(UUID getOrderId,GetProductResponseDto getProduct, OrderCreateRequestDto dto, ResponseDeliveryRegisterDto getDelivery) {
        return OrderInfoMessageRequestDto.builder()
                .orderId(getOrderId)
                // dto 필드에 productName 없어요 getName 으로 변경했습니다.
                .productName(getProduct.getName())
                .quantity(dto.getQuantity())
                .receiverName(dto.getReceiverName())
                .destination(dto.getAddress())
                .requestMessage(dto.getMessage())
                .deliveryId(getDelivery.getId())
                .build();
    }
}

