package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

public class CaseSummaryResponse {

    private Integer id;
    private String title;
    private District district;
    private CaseStatus status;
    private LocalDateTime createdAt;

    public CaseSummaryResponse(Integer id, String title, District district, CaseStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public District getDistrict() {
        return district;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
