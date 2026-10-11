package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.OfficerSize;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.entity.enums.OfficerStatus;

public class OfficerUpdateResponse {

    private Integer id;
    private String nickname;
    private String name;
    private String species;
    private OfficerSize size;
    private District district;
    private OfficerStatus status;

    public OfficerUpdateResponse(
            Integer id,
            String nickname,
            String name,
            String species,
            OfficerSize size,
            District district,
            OfficerStatus status) {

        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.species = species;
        this.size = size;
        this.district = district;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
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
