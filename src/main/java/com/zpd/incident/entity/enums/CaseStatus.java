package com.zpd.incident.entity.enums;

// 사건 상태. 최초 접수는 WAITING이며 배정·상태 변경은 관리자가 처리한다.
public enum CaseStatus {
    WAITING("수사 대기"), // 담당자 없는 배정 대기 상태. 최초 배정 시 IN_PROGRESS로 변경
    IN_PROGRESS("수사 중"), // 담당 경찰관이 배정되어 수사 중인 상태
    SOLVED("해결"), // 수사 중 사건이 해결된 상태
    FALSE_ALARM("오신고"), // 수사 중 오신고로 확인된 상태
    CLOSED("종결"), // 수사 기간 경과 등의 사유로 미해결 상태에서 수사를 종료한 최종 상태
    CANCELLED("취소"); // WAITING에서만 가능한 관리자 취소 처리. 이후 변경 불가

    private final String description;

    CaseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
