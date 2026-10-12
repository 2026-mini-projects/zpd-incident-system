package com.zpd.incident.dto.request;

import com.zpd.incident.entity.enums.District;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// 사건 접수 요청 DTO
public class CaseCreateRequest {

    @NotBlank(message = "사건 제목은 필수입니다.")
    @Size(max = 100, message = "사건 제목은 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "사건 내용은 필수입니다.")
    private String content;

    @NotNull(message = "사건 발생 구역은 필수입니다.")
    private District district;

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
