package com.zpd.incident.controller;

import com.zpd.incident.service.OfficerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zpd.incident.dto.response.OfficerDetailResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.zpd.incident.common.PageResponse;
import com.zpd.incident.dto.response.OfficerSummaryResponse;
import org.springframework.web.bind.annotation.RequestParam;
import com.zpd.incident.entity.enums.OfficerStatus;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.entity.enums.OfficerSize;

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

    @GetMapping
    public Map<String, PageResponse<OfficerSummaryResponse>> getOfficers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "status", required = false)
            OfficerStatus status,
            @RequestParam(name = "district", required = false)
            District district,
            @RequestParam(name = "sizeType", required = false)
            OfficerSize sizeType,
            @RequestParam(name = "keyword", required = false)
            String keyword,
            @RequestParam(name = "sort", defaultValue = "name,asc")
            String sort) {

        PageResponse<OfficerSummaryResponse> response =
                officerService.getOfficers(
                        page, size, status, district, sizeType, keyword, sort
                );

        return Map.of("data", response);
    }
}
