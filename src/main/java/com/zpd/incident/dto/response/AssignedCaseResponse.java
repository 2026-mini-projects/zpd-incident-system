package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

// 경찰관에게 배정된 본인 담당 사건 목록 응답 DTO
public class AssignedCaseResponse {

    private Integer id;                    // 사건 번호
    private String title;                  // 사건 제목
    private District district;             // 사건 발생 구역
    private CaseStatus status;             // 사건 상태
    private CaseReporterResponse reporter; // 사건 신고자 정보
    private LocalDateTime createdAt;       // 사건 접수 일시

    public AssignedCaseResponse(Integer id, String title, District district, CaseStatus status, CaseReporterResponse reporter, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.status = status;
        this.reporter = reporter;
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

    public CaseReporterResponse getReporter() {
        return reporter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
