package com.zpd.incident.controller;

import com.zpd.incident.dto.CaseSummaryResponse;
import com.zpd.incident.service.CaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    public List<CaseSummaryResponse> getAllCases() {

        return caseService.getAllCases();
    }
}
