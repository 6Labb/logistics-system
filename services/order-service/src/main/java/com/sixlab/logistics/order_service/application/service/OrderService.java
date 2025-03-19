package com.sixlab.logistics.order_service.application.service;

import com.sixlab.logistics.common.shared.exception.OutOfStockException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.order_service.application.client.CompanyClient;
import com.sixlab.logistics.order_service.application.client.DeliveryClient;
import com.sixlab.logistics.order_service.application.client.HubClient;
import com.sixlab.logistics.order_service.application.client.ProductClient;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;

import com.sixlab.logistics.order_service.application.dto.request.OrderInfoUpdateRequestDto;
import com.sixlab.logistics.order_service.application.dto.request.RequestDeliveryRegisterDto;
import com.sixlab.logistics.order_service.application.dto.response.*;
import com.sixlab.logistics.order_service.application.dto.response.GetCompanyResponseDto.Type;
import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.infrastructure.persistence.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

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
    private final HubClient hubClient;

    private final OrderJpaRepository orderJpaRepository;


    public OrderCreateResponseDto createOrder(OrderCreateRequestDto dto) throws Exception{
        log.info("service 계층: createOrder() 호출됨");
        // 1. 모든 로그인 사용자 (주문자) 가능
        // jwt 를 통해 user 의 id 를 얻어낸다.

        // 2. 상품 테이블에서 상품이 존재하는지
        // 상품 id 를 전달하면서 상품조회 서비스 호출
        // --> 상품 조회 서비스의 권한은 MASTER, HUB_MANAGER, TRADE_PARTNER

        // ApiResponse<GetProductResponseDto> productById = productClient.getProductById(dto.getProductId());

        // 상기코드 실행시 전달받을 객체, 지금은 Product 객체 직접 생성
        GetProductResponseDto getProduct = GetProductResponseDto.builder()
                .id(productId) // 50c3068a-6b09-4f45-a3af-c119168a7676
                .companyId(companyId)
                .hubId(hubId)
                .productName("지우개")
                .quantity(40)
                .createdBy(40) // 이 상품을 생성한 사용자
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .build();


        if(getProduct == null) {
            log.info("상품이 존재하지 않을 경우 이 로그가 찍힌다.");
            throw new ResourceNotFoundException("존재하지 않는 상품입니다.");
        }

        // (상품 id 기반으로 객체를 전달받은상태) & 요청 상품 수량이 재고 수량보다 적거나 같은지 확인
        // 상품 객체에 허브 id, 공급업체 id, (재고) 수량 필드 등이 존재하고,
        // 여기서 (재고) 수량은 허브 id 가 관리하는 수량으로 정의함 (팀원들끼리 합의봄)
        // *** 클라이언트로부터 요청수량 0 미만으로는 받지 못하도록 설정 @Min(value=1)
        if(dto.getQuantity() > getProduct.getQuantity()) {
            log.info("요청 수량이 재고 수량보다 많음");
            log.info("요청 수량: {}, 재고 수량: {}", dto.getQuantity(), getProduct.getQuantity());
            String data = "요청 수량이 재고 수량을 초과했습니다. 최대 주문 가능 수량을 확인해 주세요.\n 최대 주문 가능 수량: "+getProduct.getQuantity();
            throw new OutOfStockException(data);
        }

        // 3. company-service 의 조회 메서드 호출하기
        // ApiResponse<GetCompanyResponseDto> companyById = companyClient.getCompanyById(dto.getReceiverId());

        // 상기 코드 호출시 하기의 객체를 전달받음
        GetCompanyResponseDto getCompanyInfo = GetCompanyResponseDto.builder()
                .type(Type.RECEIVER)
                .name("스파르타")
                // 업체 정보 생성한 일자 정보
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .hubId(hubId) // 업체 테이블에 왜 hub id 가??
                .id(receiverCompanyId) // 수령업체 id 여야함. 지금 이 객체에서는
                .build();

        if(getCompanyInfo == null || getCompanyInfo.getType() != Type.RECEIVER) {
            // company-service 의 조회 메서드 호출 후 전달받은 company 객체가 null 또는
            // null 이 아니라면 --> 객체에서 업체 타입의 정보가 RECEIVER 가 아니라면
            throw new ResourceNotFoundException("수령업체 정보가 존재하지 않습니다.");
        }

        // 4. 배송등록 마이크로 서비스 호출에 전달할 데이터 생성
        RequestDeliveryRegisterDto requestDeliveryRegisterDto = RequestDeliveryRegisterDto.builder()
                .supplierCompanyId(getProduct.getCompanyId()) // 공급업체 id
                .receiverCompanyId(getCompanyInfo.getId()) // 수령업체 id
                .deliveryAddress(dto.getAddress()) // 배송지
                .receiveName(dto.getReceiverName()) // 수령인
                .build();

        // 하기와 같은 코드로 배송 서비스 호출 --> 배송 서비스의 배송등록 메서드 호출됨
        // ApiResponse<GetProductResponseDto> getDelivery = deliveryClient.requestDeliveryRegister(requestDeliveryRegisterDto);

        ResponseDeliveryRegisterDto getDelivery = ResponseDeliveryRegisterDto.builder()
                .companyDeliveryAgentId(deliveryAgentId)
                .deliveryAddress(dto.getAddress())
                .status(ResponseDeliveryRegisterDto.DeliveryStatus.WAITING)
                .receiveName(dto.getReceiverName())
                .id(deliveryId)
                .build();

       if(getDelivery == null) {
            log.info("배송 서비스 호출 --> 배송등록 메서드 호출 --> 실패");
            throw new ResourceNotFoundException("배송등록 서비스 호출에 실패하였습니다.");
        }

        // 5. Order 엔터티 객체 만들어서 저장하기
        Order order = dto.toEntity(getProduct.getCompanyId(), userId, getDelivery.getId());
        Order savedOrder = orderJpaRepository.save(order);
        return new OrderCreateResponseDto(savedOrder);
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

        // 2.
        // 기존 주문했던 물품 요청 수량과 수정 요청 수량이 다르다면

        log.info("기존 물품 요청 수량: {}, 수정 요청 수량: {}", order.getQuantity(), dto.getQuantity());
        if(!order.getQuantity().equals(dto.getQuantity()))
        {
            // 3. productId 를 기반으로 상품 서비스 조회 호출
            // *** 추후 하기 메서드 추출(refactoring) 고려
            // ApiResponse<GetProductResponseDto> getProduct = productClient.getProductById(order.getProductId());

            // 상기 상품 서비스 호출시 전달받을 물품 객체
            GetProductResponseDto getProduct = GetProductResponseDto.builder()
                    .id(productId) // 50c3068a-6b09-4f45-a3af-c119168a7676
                    .companyId(companyId)
                    .hubId(hubId)
                    .productName("지우개")
                    .quantity(40)
                    .createdBy(40) // 이 상품을 생성한 사용자
                    .createdAt(LocalDateTime.now().minusWeeks(1))
                    .build();

            if(getProduct == null) {
                log.info("상품이 존재하지 않을 경우 이 로그가 찍힌다.");
                throw new ResourceNotFoundException("존재하지 않는 상품입니다.");
            }

            if(dto.getQuantity() > getProduct.getQuantity()) {
                log.info("요청 수량이 재고 수량보다 많음");
                log.info("요청 수량: {}, 재고 수량: {}", dto.getQuantity(), getProduct.getQuantity());
                String data = "요청 수량이 재고 수량을 초과했습니다. 최대 주문 가능 수량을 확인해 주세요.\n 최대 주문 가능 수량: "+getProduct.getQuantity();
                throw new OutOfStockException(data);
            }

            order.setQuantity(dto.getQuantity());
        }

        log.info("기존 요청 메시지: {}, 수정 요청 메시지: {}", order.getMessage(), dto.getMessage());
        if(!order.getMessage().trim().equals(dto.getMessage().trim())) order.setMessage(dto.getMessage());
        Order updatedOneOrder = orderJpaRepository.save(order);
        return new OrderInfoUpdateResponseDto(updatedOneOrder);



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
        // 3) hub 관리자 서비스 호출: hub 매니저 객체에서 userId 를 얻어야 한다.
        // --> 그리고 비교해서 맞지 않다면 접근 권한이 없다고 리턴한다.

        // 3. 상품 서비스에게 수량 만큼의 복원을 요청한다.


        // 4. 배송 서비스에게 배송 id 삭제를 요청한다.

        // 5. 주문을 삭제한다.
        // 소프트 삭제 로직 설계중 deleteBy 타입에 필요한 userId 타입 논의?
        // deleteBy 타입에 대입돨 값을 논의(정의)하는 중
        order.delete(userId);
        return new OrderDeleteResponseDto(order);
    }

    // --------------------------------------------------------------
    // 모든 주문내역 조회: MASTER
    public List<OrderFindOneResponseDto> findAllOrders() {
        List<Order> findOrderList = orderJpaRepository.findAll();
        List<OrderFindOneResponseDto> orderList = new ArrayList<>();

        if(!findOrderList.isEmpty()) {
            for(Order order : findOrderList) {
                orderList.add(new OrderFindOneResponseDto(order));
            }
        }
        return orderList;
    }

    // 모든 주문내역 조회: DELIVERY_AGENT, TRADE_PARTNER
    public List<OrderFindOneResponseDto> findAllOrders(Long userId) {
        List<Order> findOrderList = orderJpaRepository.findAllByUserId(userId);
        List<OrderFindOneResponseDto> orderList = new ArrayList<>();

        if(!findOrderList.isEmpty()) {
            for(Order order : findOrderList) {
                orderList.add(new OrderFindOneResponseDto(order));
            }
        }
        return orderList;
    }
}
