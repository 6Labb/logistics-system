package com.sixlab.logistics.delivery_service.deliveryAgent.application.service;

import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentRequestDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentResponseDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.application.dto.DeliveryAgentSearchDto;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgent;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.entity.DeliveryAgentType;
import com.sixlab.logistics.delivery_service.deliveryAgent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAgentService {

    private final DeliveryAgentRepository deliveryAgentRepository;

    // 배송 담당자 리스트 조회
    public Page<DeliveryAgentResponseDto> getAllDeliveryAgent(DeliveryAgentSearchDto searchDto, Pageable pageable) {
        // 권한 받아오기
            // 관리자는 전부 가능
            // 허브담당자는 본인의 허브 배송 담당자 리스트만 가능
            // 배송담당자는 본인의 배송 담당자 리스트만 가능

        return deliveryAgentRepository.searchDeliveryAgentList(searchDto, pageable);

    }

    // 배송 담당자 개별 조회
    public DeliveryAgentResponseDto getDeliveryAgent(Long userId) {
        // 권한 받아오기
            // 관리자는 전부 가능
            // 허브담당자는 본인의 허브 배송 담당자만 가능
            // 배송담당자는 본인의 배송 담당자만 가능

        // id로 배송 객체 찾기
        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 조회 by 허브 ID
    @Transactional
    public DeliveryAgentResponseDto getDeliveryAgentByHubIdAndType(UUID hubId, DeliveryAgentType type) {
        // 권한 받아오기
        // 관리자는 전부 가능
        // 허브담당자는 본인의 허브 배송 담당자만 가능
        // 배송담당자는 본인의 배송 담당자만 가능

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
    public DeliveryAgentResponseDto updateDeliveryAgent(Long userId, DeliveryAgentRequestDto requestDto) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송 담당자만 가능

        // id로 배송 객체 찾기
        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        // 배송 정보 업데이트
        deliveryAgent.updateDeliveryAgent(requestDto);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 삭제
    @Transactional
    public DeliveryAgentResponseDto deleteDeliveryAgent(Long userId) {
        // 권한 받아오기
            // 관리자
            // 허브담당자는 본인의 허브 배송 담당자만 가능

        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        deliveryAgent.delete(null);

        return new DeliveryAgentResponseDto(deliveryAgent);
    }

    // 배송 담당자 생성
    @Transactional
    public DeliveryAgentResponseDto createDeliveryAgent(DeliveryAgentRequestDto requestDto) {
        // 관리자만 가능

        DeliveryAgent deliveryAgent = new DeliveryAgent(requestDto);
        DeliveryAgent savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);

        return new DeliveryAgentResponseDto(savedDeliveryAgent);
    }

}
