package com.zpd.incident.dto.request;

import com.zpd.incident.entity.enums.CaseStatus;

public class CaseStatusUpdateRequest {

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
