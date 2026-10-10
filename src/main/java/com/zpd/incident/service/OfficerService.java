package com.zpd.incident.service;

import com.zpd.incident.repository.OfficerRepository;
import org.springframework.stereotype.Service;
import com.zpd.incident.dto.response.OfficerDetailResponse;
import com.zpd.incident.entity.Officer;
import com.zpd.incident.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OfficerService {

    private final OfficerRepository officerRepository;

    public OfficerService(OfficerRepository officerRepository) {
        this.officerRepository = officerRepository;
    }

    @Transactional(readOnly = true)
    public OfficerDetailResponse getOfficerDetail(Integer officerId) {

        Officer officer = officerRepository.findById(officerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "경찰관을 찾을 수 없습니다."
                ));

        User user = officer.getUser();

        return new OfficerDetailResponse(
                officer.getId(),
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                officer.getName(),
                officer.getSpecies(),
                officer.getSize(),
                officer.getDistrict(),
                officer.getStatus()
        );
    }
}
