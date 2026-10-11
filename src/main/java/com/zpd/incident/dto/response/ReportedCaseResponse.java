package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

// 시민이 신고한 본인 사건 목록 응답 DTO
public class ReportedCaseResponse {

    private Integer id;                  // 사건 번호
    private String title;                // 사건 제목
    private District district;           // 사건 발생 구역
    private CaseStatus status;           // 사건 상태
    private CaseOfficerResponse officer; // 담당 경찰관 정보, 미배정이면 null
    private LocalDateTime createdAt;     // 사건 접수 일시

    public ReportedCaseResponse(Integer id, String title, District district, CaseStatus status, CaseOfficerResponse officer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.status = status;
        this.officer = officer;
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

    public CaseOfficerResponse getOfficer() {
        return officer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
