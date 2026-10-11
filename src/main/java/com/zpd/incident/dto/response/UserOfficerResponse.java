package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.District;
import com.zpd.incident.entity.enums.OfficerSize;
import com.zpd.incident.entity.enums.OfficerStatus;

public class UserOfficerResponse {

    private final Integer id;
    private final String name;
    private final String species;
    private final OfficerSize size;
    private final District district;
    private final OfficerStatus status;

    public UserOfficerResponse(
            Integer id,
            String name,
            String species,
            OfficerSize size,
            District district,
            OfficerStatus status) {
        this.id = id;
        this.name = name;
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
