package com.zpd.incident.controller;

import com.zpd.incident.service.OfficerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zpd.incident.dto.response.OfficerDetailResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/officers")
public class OfficerController {

    private final OfficerService officerService;

    public OfficerController(OfficerService officerService) {
        this.officerService = officerService;
    }

    @GetMapping("/{officerId}")
    public Map<String, OfficerDetailResponse> getOfficerDetail(
            @PathVariable("officerId") Integer officerId) {

        OfficerDetailResponse response =
                officerService.getOfficerDetail(officerId);

        return Map.of("data", response);
    }
}
