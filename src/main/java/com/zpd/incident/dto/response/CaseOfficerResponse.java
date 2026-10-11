package com.zpd.incident.dto.response;

// 사건 응답에 표시할 담당 경찰관 요약 정보
public class CaseOfficerResponse {
    private Integer id;     // 경찰관 고유 번호
    private String name;    // 경찰관 이름

    public CaseOfficerResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
