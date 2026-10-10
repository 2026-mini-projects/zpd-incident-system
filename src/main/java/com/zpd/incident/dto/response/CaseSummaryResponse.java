package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

public class CaseSummaryResponse {

    private Integer id;
    private String title;
    private District district;
    private CaseStatus caseStatus;
    private LocalDateTime createdAt;

    public CaseSummaryResponse(Integer id, String title, District district, CaseStatus caseStatus, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.caseStatus = caseStatus;
        this.createdAt = createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
