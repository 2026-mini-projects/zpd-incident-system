package com.zpd.incident.entity.enums;

// 경찰관 동물의 신체 체급을 구분하는 코드
public enum OfficerSize {
    SMALL("소형"),
    MEDIUM("중형"),
    LARGE("대형");

    private final String description;

    OfficerSize(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
