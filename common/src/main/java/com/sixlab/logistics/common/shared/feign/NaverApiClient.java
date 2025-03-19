package com.sixlab.logistics.common.shared.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverApiClient", url = "${naver.uri}")
public interface NaverApiClient {

    //위 경도 경로검색
    @GetMapping("map-direction/v1/driving")
    String getDirections(@RequestParam("start") String start,
                         @RequestParam("waypoints") String waypoints,
                         @RequestParam("goal") String goal);

    //주소검색 ( ex)경기도 성남시 분당구 불정로 6 )
    @GetMapping("/map-geocode/v2/geocode")
    String getGeocode(@RequestParam("roadAddress") String roadAddress);

}
