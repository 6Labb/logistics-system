package com.sixlab.logistics.company_service.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

}
