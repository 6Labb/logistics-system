package com.sixlab.logistics.slack_ai_service.Messenger.application.dto.naver;

public record LocationRequestDto(
        //주소
        String query
        //검색 중심 좌표(경도,위도) - 필요시 주석풀고 사용
        //String coordinate
) {
}
