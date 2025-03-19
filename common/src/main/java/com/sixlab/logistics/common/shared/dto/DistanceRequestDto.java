package com.sixlab.logistics.common.shared.dto;

public record DistanceRequestDto(
        String start,
        String waypoints,
        String goal
) {}
/* waypoints 사용시 '| (Pipe char)'로 구분하여 최대 5개의 경유지 입력 가능
   동일 경유지의 좌표가 2개인 경우, ':'로 */

