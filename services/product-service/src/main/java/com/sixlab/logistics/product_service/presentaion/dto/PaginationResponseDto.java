package com.sixlab.logistics.product_service.presentaion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaginationResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

    public PaginationResponseDto(List<T> content, int page, int size, long totalItems, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
