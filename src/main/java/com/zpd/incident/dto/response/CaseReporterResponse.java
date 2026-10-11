package com.zpd.incident.dto.response;

// 사건 응답에 표시할 신고자 요약 정보
public class CaseReporterResponse {
    private Integer id;             // 신고자의 사용자 고유 번호
    private String nickname;        // 신고자 닉네임

    public CaseReporterResponse(Integer id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
