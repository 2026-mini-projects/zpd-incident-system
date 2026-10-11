package com.zpd.incident.controller;

import com.zpd.incident.dto.response.CaseDetailResponse;
import com.zpd.incident.dto.response.CaseSummaryResponse;
import com.zpd.incident.service.CaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 사건 목록 및 단건 상세 조회 요청을 처리하는 REST Controller
@RestController
@RequestMapping("/api/v1/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    // 전체 사건을 요약 정보 목록으로 조회
    @GetMapping
    public List<CaseSummaryResponse> getAllCases() {

        return caseService.getAllCases();
    }

    // URL로 전달받은 사건 번호에 해당하는 상세 정보를 조회
    @GetMapping("/{caseId}")
    public CaseDetailResponse getCaseById(@PathVariable Integer caseId){
        return caseService.getCaseById(caseId);
    }
}
