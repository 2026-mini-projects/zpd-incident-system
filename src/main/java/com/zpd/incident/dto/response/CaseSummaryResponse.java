package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

// 전체 사건 목록에 표시할 사건 요약 응답 DTO
public class CaseSummaryResponse {

    private Integer id;                 // 사건 번호
    private String title;               // 사건 제목
    private District district;          // 사건 발생 구역
    private CaseStatus status;          // 사건 상태
    private LocalDateTime createdAt;    // 사건 접수 일시

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
