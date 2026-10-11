package com.zpd.incident.dto.request;

import com.zpd.incident.entity.enums.OfficerSize;
import com.zpd.incident.entity.enums.District;

public class OfficerUpdateRequest {

    private String nickname;
    private String name;
    private String species;
    private OfficerSize size;
    private District district;

    public OfficerUpdateRequest() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public OfficerSize getSize() {
        return size;
    }

    public void setSize(OfficerSize size) {
        this.size = size;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
