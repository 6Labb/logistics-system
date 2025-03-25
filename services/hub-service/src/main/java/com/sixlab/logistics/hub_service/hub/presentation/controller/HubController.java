package com.sixlab.logistics.hub_service.hub.presentation.controller;


import com.sixlab.logistics.common.shared.response.ApiResponse;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubCreateResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hub.HubUpdateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerCreateRequestDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerCreateResponseDto;
import com.sixlab.logistics.hub_service.hub.application.dto.hubmanager.HubManagerResponseDto;
import com.sixlab.logistics.hub_service.hub.application.service.HubManagerService;
import com.sixlab.logistics.hub_service.hub.application.service.HubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/hubs")
public class HubController {

    private final HubService hubService;
    private final HubManagerService hubManagerService;

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping
    public ApiResponse<HubResponseDto> createHub(@Valid @RequestBody HubCreateRequestDto requestDto) {
        HubResponseDto response = hubService.createHub(requestDto);
        return ApiResponse.success(response, "허브가 생성되었습니다.");
    }

    @GetMapping("/{id}")
    public ApiResponse<HubResponseDto> getHub(@PathVariable UUID id) {
        HubResponseDto response = hubService.getHubById(id);
        return ApiResponse.success(response, "허브를 조회하였습니다.");
    }

    @PreAuthorize("hasRole('MASTER')")
    @PutMapping("/{id}")
    public ApiResponse<HubResponseDto> updateHub(
            @PathVariable UUID id,
            @Valid @RequestBody HubUpdateRequestDto requestDto) {

        HubResponseDto response = hubService.updateHub(id, requestDto);
        return ApiResponse.success(response, "허브가 수정되었습니다.");
    }

    @PreAuthorize("hasRole('MASTER')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteHub(@PathVariable UUID id) {
        hubService.deleteHub(id);
        return ApiResponse.success(null, "허브매니저가 삭제되었습니다.");
    }

    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<List<HubResponseDto>> searchHubs(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "hubName") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<HubResponseDto> result = hubService.searchHubs(keyword, sort, order, page, size);
            return ApiResponse.success(result.getContent(), "허브 검색 결과");

        } catch (Exception e) {
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "허브 검색 중 오류가 발생했습니다.");
        }
    }

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping("/managers")
    public ApiResponse<HubManagerCreateResponseDto> createHubManager(@Valid @RequestBody HubManagerCreateRequestDto request) {

        try {
            HubManagerCreateResponseDto response = hubManagerService.createManager(request);
            return ApiResponse.success(response, "HubManager created");
        } catch (IllegalArgumentException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalStateException e) {
            return ApiResponse.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "허브매니저 생성 중 오류 발생");
        }


    }

    @GetMapping("/managers/{userId}")
    public ApiResponse<HubManagerResponseDto> getHubManagerByUserId(@PathVariable Long userId) {
        HubManagerResponseDto response = hubService.getManagerByUserId(userId);
        return ApiResponse.success(response, "허브매니저를 조회했습니다.");
    }






}
