package com.zpd.incident.entity.enums;

// 사건 발생 구역과 경찰관 관할 구역의 공통 코드. 다른 구역의 경찰관에게도 배정 가능하다.
public enum District {
    DOWNTOWN("중앙 본부"),
    SAHARA_SQUARE("사하라 광장"),
    TUNDRA_TOWN("툰드라 타운"),
    RAINFOREST("열대우림"),
    LITTLE_RODENTIA("리틀 로덴시아");

    private final String description;

    District(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
