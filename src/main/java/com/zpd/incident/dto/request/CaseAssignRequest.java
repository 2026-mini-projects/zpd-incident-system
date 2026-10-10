package com.zpd.incident.dto.request;

// 사건 담당 경찰관 배정 및 변경 요청 DTO
public class CaseAssignRequest {

    // 배정할 경찰관 고유 번호
    private Integer officerId;

    public CaseAssignRequest() {
    }

    public CaseAssignRequest(Integer officerId) {
        this.officerId = officerId;
    }

    public Integer getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Integer officerId) {
        this.officerId = officerId;
    }
}
