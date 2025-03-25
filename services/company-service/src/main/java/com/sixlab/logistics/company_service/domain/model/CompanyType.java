package com.sixlab.logistics.company_service.domain.model;

public enum CompanyType {
    RECEIVER("수령업체"),
    SUPPLIER("공급업체");

    private final String description;

    // 생성자
    CompanyType(String description) {
        this.description = description;
    }

    // description 반환
    public String getDescription() {
        return description;
    }
}
