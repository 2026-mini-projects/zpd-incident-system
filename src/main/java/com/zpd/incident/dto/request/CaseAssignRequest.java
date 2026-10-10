package com.zpd.incident.dto.request;

public class CaseAssignRequest {

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
