package com.sixlab.logistics.delivery_service.deliveryAgent.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.domain.model.DeliveryRoute;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAgentServiceImpl implements DeliveryAgentService {

    private final HubService hubService;
    private final DeliveryAgentRepository deliveryAgentRepository;

    // 배송 담당자 리스트 조회
    @Override
    public Page<DeliveryAgentResponseDto> getAllDeliveryAgent(DeliveryAgentSearchDto searchDto, Pageable pageable, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        // 관리자는 전부 가능
        if (currentRole.equals(Role.MASTER)){
            return deliveryAgentRepository.searchDeliveryAgentListForMaster(searchDto, pageable);
        }
        // 허브담당자는 본인의 허브 소속 배송 담당자 리스트만 가능
        if (currentRole.equals(Role.HUB_MANAGER)) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 소속허브id를 찾을 수 없습니다.");
            }
            return deliveryAgentRepository.searchDeliveryAgentListForHubMaster(searchDto, pageable, hubManager.getHubId());
        }

        throw new AccessDeniedException("배송담당자 리스트 조회 권한이 없습니다.");
    }

    // 배송 담당자 개별 조회
    @Override
    public DeliveryAgentResponseDto getDeliveryAgent(Long userId, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryAgent deliveryAgent;

        // 배송담당자는 본인의 배송만 가능
        if (currentRole.equals(Role.DELIVERY_AGENT)) {
            if (!userId.equals(currentUserId)) {
                throw new AccessDeniedException("본인의 정보만 조회할 수 있습니다.");
            }
            deliveryAgent = deliveryAgentRepository.findByUserId(currentUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 담당자 정보를 찾을 수 없습니다."));
        } else {
            // 다른 역할(MASTER, HUB_MANAGER)은 공통 권한 로직 사용
            deliveryAgent = findDeliveryWithAuthorization(userId, currentRole, currentUserId, "조회");
        }

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 조회 by 허브 ID - 배송경로생성에 사용
    @Override
    @Transactional
    public DeliveryAgentResponseDto getDeliveryAgentByHubIdAndType(UUID hubId, DeliveryAgentType type) {

        // id랑 타입으로 배송 담당자 찾기
        List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findByHubIdAndTypeOrderByDeliverySequenceAsc(hubId, type);

        // 증가순이니, 첫 번째 인덱스에 있는 배송 담당자 찾기
        DeliveryAgent deliveryAgent = deliveryAgents.isEmpty() ? null : deliveryAgents.get(0);

        // 배송 담당자가 비어있으면 오류 발생
        if (deliveryAgent == null) {
            throw new ResourceNotFoundException();
        }

        // 마지막 인덱스에 있는 배송 담당자의 순번 = 가장 높은 순번
        Integer highestSequence = deliveryAgents.get(deliveryAgents.size() - 1).getDeliverySequence();

        // 가장 높은 순번 + 1로 순번 업데이트
        deliveryAgent.updateSequence(highestSequence + 1);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 수정
    @Override
    @Transactional
    public DeliveryAgentResponseDto updateDeliveryAgent(Long userId, DeliveryAgentRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryAgent deliveryAgent = findDeliveryWithAuthorization(userId, currentRole, currentUserId, "수정");

        deliveryAgent.updateDeliveryAgent(requestDto);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 삭제
    @Override
    @Transactional
    public DeliveryAgentResponseDto deleteDeliveryAgent(Long userId, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryAgent deliveryAgent = findDeliveryWithAuthorization(userId, currentRole, currentUserId, "삭제");

        deliveryAgent.delete(currentUserId);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 생성
    @Override
    @Transactional
    public DeliveryAgentResponseDto createDeliveryAgent(DeliveryAgentRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();

        // 관리자만 가능
        if(currentRole.equals(Role.MASTER)) {
            DeliveryAgent deliveryAgent = new DeliveryAgent(requestDto);
            DeliveryAgent savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);

            return new DeliveryAgentResponseDto(savedDeliveryAgent);
        }
        else {
            throw new AccessDeniedException("배송 담당자 생성 권한이 없습니다.");
        }

    }

    // 권한 공통 로직
    private DeliveryAgent findDeliveryWithAuthorization(Long userId, Role currentRole, Long currentUserId, String operation) {
        // 관리자는 전부 가능
        if (currentRole.equals(Role.MASTER)) {
            return deliveryAgentRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 담당자 정보를 찾을 수 없습니다."));
        }

        // 허브담당자는 본인의 허브 배송만 가능
        if (currentRole.equals(Role.HUB_MANAGER)) {
            // 소속허브id 조회
            UUID hubId = getHubIdByUserId(currentUserId);
            return deliveryAgentRepository.findByUserIdAndHubId(userId, hubId)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 담당자의 " + operation + " 권한이 없습니다."));
        }

        throw new AccessDeniedException("배송 담당자의 " + operation + " 권한이 없습니다.");
    }

    // HubId 조회 공통 로직
    private UUID getHubIdByUserId(Long userId) {
        HubManagerResponseDto hubManager = hubService.getHubIdByUserId(userId);
        if (hubManager == null) {
            throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
        }
        return hubManager.getHubId();
    }

}
