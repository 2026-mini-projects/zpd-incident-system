package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

// 사건 단건 상세 조회 응답 DTO
public class CaseDetailResponse {

    private Integer id;                      // 사건 번호
    private String title;                    // 사건 제목
    private String content;                  // 사건 상세 내용
    private District district;               // 사건 발생 구역
    private CaseStatus status;               // 사건 상태
    private LocalDateTime createdAt;         // 사건 접수 일시
    private LocalDateTime closedAt;          // 사건 종료·취소 일시
    private CaseOfficerResponse officer;     // 담당 경찰관 요약 정보(고유 번호, 이름). 미배정이면 null
    private CaseReporterResponse reporter;   // 사건 신고자 요약 정보

    public CaseDetailResponse(Integer id, String title, String content, District district, CaseStatus status, LocalDateTime createdAt, LocalDateTime closedAt, CaseOfficerResponse officer, CaseReporterResponse reporter) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.district = district;
        this.status = status;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.officer = officer;
        this.reporter = reporter;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public CaseOfficerResponse getOfficer() {
        return officer;
    }

    public CaseReporterResponse getReporter() {
        return reporter;
    }
}
