package com.zpd.incident.entity.enums;

// 근무 상태. 대기·수사 중은 진행 중 담당 사건 수에 따라 서버가 관리하고, 관리자는 퇴직을 요청한다.
public enum OfficerStatus {
    STANDBY("대기 중"), // 진행 중(IN_PROGRESS) 담당 사건이 없는 상태
    WORKING("수사 중"), // 진행 중 담당 사건이 하나 이상인 상태. 추가 사건 배정 가능
    RETIRED("퇴직"); // 진행 중 담당 사건이 없을 때만 퇴직 가능. 물리 삭제 없이 유지하며 로그인·배정·복귀 불가

    private final String description;

    OfficerStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
