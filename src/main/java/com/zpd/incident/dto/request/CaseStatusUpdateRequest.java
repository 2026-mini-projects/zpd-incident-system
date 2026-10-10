package com.zpd.incident.dto.request;

import com.zpd.incident.entity.enums.CaseStatus;

// 사건 상태 변경 요청 DTO
public class CaseStatusUpdateRequest {

    /* 변경할 사건 상태.
    *  WAITING(수사 대기), IN_PROGRESS(수사 중), SOLVED(해결),
    *  FALSE_ALARM(오신고), CLOSED(종결), CANCELLED(취소)
    * */
    private CaseStatus status;

    public CaseStatusUpdateRequest() {
    }

    public CaseStatusUpdateRequest(CaseStatus status) {
        this.status = status;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }
}
