package com.sixlab.logistics.order_service.application.service;

import com.sixlab.logistics.common.shared.exception.OutOfStockException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.order_service.application.dto.request.OrderCreateRequestDto;

import com.sixlab.logistics.order_service.application.dto.request.RequestDeliveryRegisterDto;
import com.sixlab.logistics.order_service.application.dto.response.GetCompanyResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.GetCompanyResponseDto.Type;
import com.sixlab.logistics.order_service.application.dto.response.GetProductResponseDto;
import com.sixlab.logistics.order_service.application.dto.response.OrderCreateResponseDto;
import com.sixlab.logistics.order_service.domain.model.Order;
import com.sixlab.logistics.order_service.infrastructure.persistence.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final UUID productId = UUID.fromString("50c3068a-6b09-4f45-a3af-c119168a7676");
    private final UUID companyId = UUID.fromString("edb45825-cafb-457f-9179-7d544b1ec78a"); // 공급업체 id
    private final UUID receiverCompanyId = UUID.fromString("c0502b76-1beb-4d6d-a2d3-3f61ca1b7574"); // 수령업체 id
    // 허브 id: GetProductResponseDto 에 상품 id, 공급업체 id, 수량 등의 필드 정보가 존재하고,
    // 해당 허브 id 는 상품 id 를 관리하고 있으며 해당 상품의 수량은 quantity 필드를 참고하면 된다.
    private final UUID hubId = UUID.fromString("69199641-5072-4036-a899-af524390fb93");
    private final UUID userId = UUID.fromString("da66e6a7-a123-4b34-8724-7dd9080fa928");

    private final OrderJpaRepository orderJpaRepository;


    public OrderCreateResponseDto createOrder(OrderCreateRequestDto dto) throws Exception{
        log.info("service 계층: createOrder() 호출됨");
        // 1. 모든 로그인 사용자 (주문자) 가능 --> 로그인 여부는 필터에서 걸러지고(권한확인 안해도 된다고 판단)
        // & userId 가 user 테이블에 존재하는지 확인 (토큰을 통해 얻은 유저객체를 기반으로)
        // 토큰의 위조 가능성이 있어 user-service 호출해서 반환 받은 값으로 대조해야 한다고 생각하는데...

        // 2. 상품 테이블에서 상품이 존재하는지
        // 상품 id 를 전달하면서 상품조회 서비스 호출
        // --> 상품 조회 서비스의 권한은 MASTER, HUB_MANAGER, TRADE_PARTNER

        // Product 객체 직접 생성
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
        if(dto.getQuantity() > getProduct.getQuantity()) {
            log.info("요청 수량이 재고 수량보다 많음");
            log.info("요청 수량: {}, 재고 수량: {}", dto.getQuantity(), getProduct.getQuantity());
            String data = "요청 수량이 재고 수량을 초과했습니다. 최대 주문 가능 수량을 확인해 주세요.\n 최대 주문 가능 수량: "+getProduct.getQuantity();
            throw new OutOfStockException(data);
        }

        // 3. company-service 의 조회 메서드 호출하기
        GetCompanyResponseDto getCompanyInfo = GetCompanyResponseDto.builder()
                .type(Type.RECEIVER)
                .name("스파르타")
                // 업체 정보 생성한 일자 정보
                .createdAt(LocalDateTime.now().minusWeeks(1))
                .hubId(hubId) // 업체 테이블에 왜 hub id 가??
                .id(receiverCompanyId) // 수령업체 id 여야함. 지금 이 객체에서는
                .build();

        if(getCompanyInfo == null || getCompanyInfo.getType() == Type.RECEIVER) {
            // company-service 의 조회 메서드 호출 후 전달받은 company 객체가 null 또는
            // null 이 아니라면 --> 객체에서 업체 타입의 정보가 RECEIVER 가 아니라면
            throw new ResourceNotFoundException("수령업체 정보가 존재하지 않습니다.");
        }

//        Order createOrder = dto.toEntity(getProduct.getCompanyId(), userId);
//        Order savedOrder = orderJpaRepository.save(createOrder);


        // 4. 배송등록 마이크로 서비스 호출 --> 배송 객체 전달받기 --> 배송 객체에서 배송 id 값 사용하기
        RequestDeliveryRegisterDto requestDeliveryRegisterDto = RequestDeliveryRegisterDto.builder()
                .supplierCompanyId(companyId) // 공급업체 id
                .receiverCompanyId(receiverCompanyId) // 수령업체 id
                .deliveryAddress(dto.getAddress()) // 배송지
                .receiveName(dto.getReceiverName()) // 수령인
                // .receiverCompanySlackId(savedOrder.getReceiverSlackId())
                // 수령업체 id 를 통해 업체 조회 서비스를 호출하고,
                // 얻은 업체 객체에서 업체 타입이 수령업체인지 한번더 확인후
                // 업체명을 얻어서 하기 필드 값에 설정하기

                // 지금 전달받은 토큰의 role 이 조회할 수 있는 권한임.
                // 지금 전달받은 토큰과(토큰을 header 에 심어서) companyId 를 전달하면서
                // company-service 조회기능 호출하기
                // 호출후 전달받은 company 객체에서 업체명을 꺼내서 설정하기
                .build();

        // 5. Order 엔터티 객체 만들어서 저장하기




        return null;
    }
}
