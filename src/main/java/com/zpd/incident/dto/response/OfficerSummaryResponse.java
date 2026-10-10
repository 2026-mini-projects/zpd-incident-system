package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.OfficerSize;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.entity.enums.OfficerStatus;

public class OfficerSummaryResponse {

    private Integer id;
    private String name;
    private String nickname;
    private String species;
    private OfficerSize size;
    private District district;
    private OfficerStatus status;

    public OfficerSummaryResponse(
            Integer id,
            String name,
            String nickname,
            String species,
            OfficerSize size,
            District district,
            OfficerStatus status) {

        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.species = species;
        this.size = size;
        this.district = district;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSpecies() {
        return species;
    }

    public OfficerSize getSize() {
        return size;
    }

    public District getDistrict() {
        return district;
    }

    public OfficerStatus getStatus() {
        return status;
    }
}
