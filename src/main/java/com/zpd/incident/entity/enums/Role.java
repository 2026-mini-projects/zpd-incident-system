package com.zpd.incident.entity.enums;

//역할 별 계정 권한. 실제 접근 제한은 별도의 인증·인가 로직에서 검증한다.
public enum Role {
    CITIZEN("시민"), // 전체 요약 조회, 사건 신고 및 본인 신고 사건 상세 조회
    OFFICER("경찰관"), // 전체 사건 상세 및 본인 담당 사건 조회. 배정·상태 변경 권한 없음
    ADMIN("관리자"); // 사건 조회·배정·상태 변경 및 경찰관 조회·수정·퇴직

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
