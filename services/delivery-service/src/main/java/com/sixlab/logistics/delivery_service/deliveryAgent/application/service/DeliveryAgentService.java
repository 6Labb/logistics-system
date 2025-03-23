package com.sixlab.logistics.delivery_service.deliveryAgent.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.common.shared.security.Role;
import com.sixlab.logistics.common.shared.security.UserDetailsImpl;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.HubService;
import com.sixlab.logistics.delivery_service.delivery.infrastructure.client.dto.HubManagerResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAgentService {

    private final HubService hubService;
    private final DeliveryAgentRepository deliveryAgentRepository;

    // 배송 담당자 리스트 조회
    public Page<DeliveryAgentResponseDto> getAllDeliveryAgent(DeliveryAgentSearchDto searchDto, Pageable pageable, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        // 관리자는 전부 가능
        if (currentRole == Role.MASTER ) {
            return deliveryAgentRepository.searchDeliveryAgentListForMaster(searchDto, pageable);
        }
        // 허브담당자는 본인의 허브 소속 배송 담당자 리스트만 가능
        if (currentRole == Role.HUB_MANAGER) {
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
    public DeliveryAgentResponseDto getDeliveryAgent(Long userId, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryAgent deliveryAgent;

        // 관리자는 전부 가능
        if(currentRole == Role.MASTER) {
            deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 담당자 정보를 찾을 수 없습니다."));
        }
        // 허브담당자는 본인의 허브 배송 담당자만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            deliveryAgent = deliveryAgentRepository.findByUserIdAndHubId(userId, hubManager.getHubId())
                    .orElseThrow(() -> new AccessDeniedException("해당 배송담당자의 조회 권한이 없습니다."));;
        }
        // 배송담당자는 본인의 배송만 가능
        else if (currentRole == Role.DELIVERY_AGENT) {
            if (!userId.equals(currentUserId)) {
                throw new AccessDeniedException("본인의 정보만 조회할 수 있습니다.");
            }
            deliveryAgent = deliveryAgentRepository.findByUserId(currentUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 담당자 정보를 찾을 수 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 담당자 조회 권한이 없습니다.");
        }

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 조회 by 허브 ID - 배송경로생성에 사용
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
    @Transactional
    public DeliveryAgentResponseDto updateDeliveryAgent(Long userId, DeliveryAgentRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryAgent deliveryAgent;

        // 관리자
        if(currentRole == Role.MASTER) {
            deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("배송 담당자 정보를 수정할 수 없습니다."));
        }
        // 허브담당자는 본인의 허브 배송만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            deliveryAgent = deliveryAgentRepository.findByUserIdAndHubId(userId, hubManager.getHubId())
            //deliveryAgent = deliveryAgentRepository.findByUserIdAndHubId(userId, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 담당자의 수정 권한이 없습니다."));
        }

        else {
            throw new AccessDeniedException("배송 수정 권한이 없습니다.");
        }

        // 배송 정보 업데이트
        deliveryAgent.updateDeliveryAgent(requestDto);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 삭제
    @Transactional
    public DeliveryAgentResponseDto deleteDeliveryAgent(Long userId, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();
        Long currentUserId = userDetails.getUserId();

        DeliveryAgent deliveryAgent;

        // 관리자
        if(currentRole == Role.MASTER) {
            deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                    .orElseThrow(ResourceNotFoundException::new);
        }
        // 허브담당자는 본인의 허브 배송 담당자만 가능
        else if (currentRole == Role.HUB_MANAGER) {
            // 소속허브id 조회
            HubManagerResponseDto hubManager = hubService.getHubIdByUserId(currentUserId);
            //TODO: 테스트용 소속허브id 조회
            //UUID hubManager = UUID.fromString("11e98756-d7a2-f948-b1b1-0242ac120001");
            if (hubManager == null) {
                throw new ResourceNotFoundException("해당 아이디로 허브 id를 찾을 수 없습니다.");
            }
            deliveryAgent = deliveryAgentRepository.findByUserIdAndHubId(userId, hubManager.getHubId())
            //deliveryAgent = deliveryAgentRepository.findByUserIdAndHubId(userId, hubManager)
                    .orElseThrow(() -> new AccessDeniedException("해당 배송 담당자의 삭제 권한이 없습니다."));
        }
        else {
            throw new AccessDeniedException("배송 삭제 권한이 없습니다.");
        }

        deliveryAgent.delete(currentUserId);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 생성
    @Transactional
    public DeliveryAgentResponseDto createDeliveryAgent(DeliveryAgentRequestDto requestDto, UserDetailsImpl userDetails) {
        // 권한 받아오기
        Role currentRole = userDetails.getUserInfo().getRole();

        // 관리자만 가능
        if(currentRole == Role.MASTER) {
            DeliveryAgent deliveryAgent = new DeliveryAgent(requestDto);
            DeliveryAgent savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);

            return new DeliveryAgentResponseDto(savedDeliveryAgent);
        }
        else {
            throw new AccessDeniedException("배송 삭제 권한이 없습니다.");
        }

    }

}
