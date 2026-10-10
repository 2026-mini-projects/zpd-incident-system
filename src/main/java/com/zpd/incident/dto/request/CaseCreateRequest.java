package com.zpd.incident.dto.request;

import com.zpd.incident.entity.enums.District;

// 사건 접수 요청 DTO
public class CaseCreateRequest {

    private String title;               // 사건 제목
    private String content;             // 사건 상세 내용
    private District district;          // 사건 발생 구역

    public CaseCreateRequest() {
    }

    public CaseCreateRequest(String title, String content, District district) {
        this.title = title;
        this.content = content;
        this.district = district;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
