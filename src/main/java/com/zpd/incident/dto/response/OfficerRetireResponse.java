package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.OfficerStatus;

public class OfficerRetireResponse {

    private final Integer id;
    private final OfficerStatus status;

    public OfficerRetireResponse(Integer id, OfficerStatus status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public OfficerStatus getStatus() {
        return status;
    }
}
