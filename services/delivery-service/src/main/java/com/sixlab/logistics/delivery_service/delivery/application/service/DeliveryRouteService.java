package com.sixlab.logistics.delivery_service.delivery.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.delivery_service.delivery.application.dto.*;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.Delivery;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.domain.entity.DeliveryRouteStatus;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRepository;
import com.sixlab.logistics.delivery_service.delivery.domain.repository.DeliveryRouteRepository;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubClient;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubRouteResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.service.DeliveryAgentService;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

    private final HubClient hubClient;
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final DeliveryAgentService deliveryAgentService;

    // 배송 경로 목록 조회
    public Page<DeliveryRouteResponseDto> getAllDeliveryRoute(DeliveryRouteSearchDto searchDto, Pageable pageable) {

        return deliveryRouteRepository.searchDeliveryRouteList(searchDto, pageable);
    }

    // 특정 배송 모든 경로 조회
    public Page<DeliveryRouteResponseDto> getAllDeliveryRouteByDeliveryId(DeliveryRouteSearchDto searchDto, Pageable pageable, UUID deliveryId) {

        //Page<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findAllByDeliveryId(pageable, deliveryId);
        //return deliveryRoutes.map(DeliveryRouteResponseDto::new);
        return deliveryRouteRepository.searchDeliveryRouteList(searchDto, pageable);
    }

    // 특정 배송 모든 경로 개별 조회
    public DeliveryRouteResponseDto getDeliveryRouteByDeliveryId(UUID deliveryId, UUID id) {

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송 경로 수정
    @Transactional
    public DeliveryRouteResponseDto updateDeliveryRoute(UUID deliveryId, UUID id, DeliveryRouteRequestDto requestDto) {
        // 권한 받아오기
            // 관리자
            // 허브담당자

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        deliveryRoute.updateDeliveryRoute(requestDto);
        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송 경로 상태 변경
    @Transactional
    public DeliveryRouteStatusResponseDto updateDeliveryRouteStatus(UUID deliveryId, UUID id, DeliveryRouteStatus status) {
        // 권한 받아오기
            // 관리자
            // 허브담당자
            // 배송담당자

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        deliveryRoute.updateDeliveryRouteStatus(status);
        return new DeliveryRouteStatusResponseDto(deliveryRoute.getStatus());
    }

    // 배송 경로 삭제
    @Transactional
    public DeliveryRouteResponseDto deleteDeliveryRoute(UUID deliveryId, UUID id) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송만 가능

        // 배송경로 id
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // 삭제 전 유효성 검사 - 배송대기 상태일 때만 삭제 가능
        if (deliveryRoute.getStatus() != DeliveryRouteStatus.WAITING) {
            throw new IllegalStateException("배송대기 상태의 배송기록만 삭제할 수 있습니다.");
        }

        // 해당 배송의 경로인지 확인
//        if (!deliveryRoute.getDelivery().getId().equals(deliveryId)) {
//            throw new IllegalArgumentException("해당 배송의 경로가 아닙니다.");
//        }

        deliveryRoute.delete(null);

        return new DeliveryRouteResponseDto(deliveryRoute);
    }

    // 배송경로 생성
    @Transactional
    public DeliveryRouteResponseDto createDeliveryRoute(UUID deliveryId) {
        // 배송 존재 여부 확인
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 배송을 찾을 수 없습니다."));

        UUID fromHubId = delivery.getFromHubId();
        UUID toHubId = delivery.getToHubId();
        DeliveryAgentType fromType = DeliveryAgentType.HUB;
        DeliveryAgentType toType = DeliveryAgentType.COMPANY;

        // 허브이동관리 id 조회
        //HubRouteResponseDto hubRoute = hubClient.getHubRouteId(fromHubId, toHubId);
        // TODO: 테스트용 허브 경로임
        HubRouteResponseDto hubRoute = new HubRouteResponseDto(
                UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac130002"),//UUID id
                UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001"),//fromHubId;
                UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120003"),//toHubId;
                80,// totalDuration; // 소요시간
                110.3// routeDistance; // 이동거리
        );

        // 동인 로직
        // 1번 로직 ***********************************************
        // 도착지 허브가 가지고 있는 순번 가져오기
        // 배송 담당자 테이블
        // 소속 허브 - 순번 - ~~
        // A - 0
        // A - 1
        // A - 2
        // B - 0
        // B - 1
        // B - 2
        // 허브 테이블
        // 허브 번호 - 순번(배정되어야 하는 배송 담당자 순번)
        // A - 0
        // B - 1
        // C - 1

        // 허브한테 순번 달라고 요청 (A)
        // A 허브
        // A 허브.순번 += 1
        // A 허브.순번 == A 허브에 소속된 배송 담당자 수 -> A 허브.순번 = 0
        // return A.허브.순번
        // 1번 로직 ***********************************************

        // 2번 로직 ***********************************************
        // 배송 담당자 (id - 허브 - 순번)
        // 가 - A - 0
        // 나 - A - 1
        // 다 - A - 2
        // 라 - B - 0
        // 마 - B - 1
        // 바 - B - 2
        // 사 - C - 0
        // 아 - C - 1
        // 자 - C - 2
        // 차 - C - 3
        // 배송 담당자 줘! (A)
        // 배송 담당자 도메인
        // -> A 허브에 소속된 배송 담당자 중 순번이 가장 낮은 배송 담당자 return (가)
        // -> 가.순번 = A 허브에 소속된 배송 담당자 중 가장 높은 순번(2) + 1 = 3
        // -> 가 - A - 3
        // -> 다음 요청때는 나 호출
        // 2번 로직 ***********************************************

        // 순번, 허브 -> 배송 담당자 테이블


        // 순번 0번이 한명만 존재하지 않음
        // 원하는 순번, 허브
        // A
        // 도착지 허브와 순번으로 배송 담당자 조회하기
        //

        // 허허브 - 허브(도착지허브id)로 배송담당자에서 조회
        //

        // 이전 배송 경로 정보에서 도착지허브id로 배정된 배송담당자 조회 -> 순번 확인하기 위해

        // 순번이 허브 첫 배송인 경우 해당 허브 소속의 배송담당자 순번 0인 사람 배정
        DeliveryAgent fromDeliveryAgent = new DeliveryAgent(deliveryAgentService.getDeliveryAgentByHubIdAndType(fromHubId, fromType)); // 공급업체 소속 허브 ID, 허브 타입
        DeliveryAgent toDeliveryAgent = new DeliveryAgent(deliveryAgentService.getDeliveryAgentByHubIdAndType(toHubId,toType)); // 수령업체 소속 허브 ID, 업체 타입
        // 공급업체 소속 허브 -> 수령업체 소속 허브 -> 수령업체
        // 공급업체 소속 허브 -> 수령업체 소속 허브 => 배송 담당자.type = 허브, 배송 담당자.hubId = 공급업체 소속 허브.id
        // 허브(
        // -> 배송 담당자 도메인에서 순번을 직접 관리하고 맞는 순번에 대한 배송 담당자를 응답
        // 헤헤


        // 배송 기록 생성
        DeliveryRoute deliveryRoute = DeliveryRoute.builder()
                .deliveryId(deliveryId)
                .estimatedDistance(hubRoute.getRouteDistance())
                .estimatedTime(hubRoute.getTotalDuration())
                .actualDistance(hubRoute.getRouteDistance())
                .actualTime(hubRoute.getTotalDuration())
                .fromHubId(delivery.getFromHubId())
                .toHubId(delivery.getToHubId())
                .companyDeliveryAgentId(toDeliveryAgent.getUserId())
                .hubDeliveryAgentId(fromDeliveryAgent.getUserId())
                .build();

        DeliveryRoute savedDeliveryRoute = deliveryRouteRepository.save(deliveryRoute);

        return new DeliveryRouteResponseDto(savedDeliveryRoute);
    }

    // 배송경로 생성
    /*
    @Transactional
    public DeliveryRouteResponseDto createDeliveryRoute(UUID deliveryId) {
        // 배송 존재 여부 확인
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 배송을 찾을 수 없습니다."));

        Double estimatedDistance = Double.valueOf("30");
        Integer estimatedTime = Integer.valueOf("15");

        // 배송 기록 생성
        DeliveryRoute deliveryRoute = DeliveryRoute.builder()
                .deliveryId(deliveryId)
                .estimatedDistance(estimatedDistance)
                .estimatedTime(estimatedTime)
                .actualDistance(estimatedDistance)
                .actualTime(estimatedTime)
                .fromHubId(delivery.getFromHubId())
                .toHubId(delivery.getToHubId())
                //.companyDeliveryAgentId(delivery.getCompanyDeliveryAgentId())
                //.hubDeliveryAgentId(delivery.getHubDeliveryAgentId())
                .build();

        DeliveryRoute savedDeliveryRoute = deliveryRouteRepository.save(deliveryRoute);

        return new DeliveryRouteResponseDto(savedDeliveryRoute);
    }
    */

    // 1. 배송담당자 -> 배송담당자를 요청하는 응답이 을 때 순번으로 리턴하는 메소드

}
