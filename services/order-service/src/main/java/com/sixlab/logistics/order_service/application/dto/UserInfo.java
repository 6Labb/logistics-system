package com.sixlab.logistics.order_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserInfo {
    private Long userId;
    private Role role;

    public enum Role {
        // 마스터: 시스템 전체를 관리하는 최상위 관리자
        MASTER
        // 허브 관리자: 물류 허브(중앙 물류 센터) 운영 및 관리
        , HUB_MANAGER
        // 배송 담당자: 배송 및 물류 운송을 담당
        , DELIVERY_AGENT
        // 공급/수령업체 담당자: 공급업체와 수령업체를 포함한 외부 업체 담당
        , TRADE_PARTNER
    }
}
