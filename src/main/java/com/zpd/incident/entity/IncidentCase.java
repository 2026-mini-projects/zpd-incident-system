package com.zpd.incident.entity;

import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;

import java.time.LocalDateTime;

public class IncidentCase {

    private Integer caseId;                        // 사건 고유 번호 (DB에서 자동 생성하며 저장 전에는 null)
    private String title;                         // 사건 제목 (최대 100자)
    private String content;                      // 사건 상세 내용
    private District district;                   // 사건 발생 구역
    private CaseStatus caseStatus;              // 사건 진행 상태 (신규 접수 시 WAITING)
    private User reporter;                     // 신고한 시민의 User 객체 (DB의 reporter_id로 연결 예정)
    private Officer officer;                  // 담당 경찰관의 Officer 객체. 최초 접수 시 null (미배정)
    private LocalDateTime createdAt;         // 사건 접수 일시
    private LocalDateTime closeAt;          // 사건 종료·취소 일시 (SOLVED, FALSE_ALARM, CLOSED, CANCELLED 처리 시 기록 예정)

    public IncidentCase() {
    }

    // 신규 사건 생성 (ID는 DB에서 생성하고 담당 경찰관과 종료 일시는 null로 유지)
    public IncidentCase(String title, String content, District district, User reporter) {
        this.title = title;
        this.content = content;
        this.district = district;
        this.reporter = reporter;
        this.caseStatus = CaseStatus.WAITING;       // 최초 상태는 수사 대기로 지정
        this.createdAt = LocalDateTime.now();       // 객체 생성 시점의 접수 일시 기록
    }

    public Integer getCaseId() {
        return caseId;
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

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public User getReporter() {
        return reporter;
    }

    public Officer getOfficer() {
        return officer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCloseAt() {
        return closeAt;
    }

}
