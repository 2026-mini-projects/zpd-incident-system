package com.zpd.incident.controller;

import com.zpd.incident.common.CurrentUserProvider;
import com.zpd.incident.common.PageResponse;
import com.zpd.incident.dto.request.CaseAssignRequest;
import com.zpd.incident.dto.request.CaseCreateRequest;
import com.zpd.incident.dto.request.CaseStatusUpdateRequest;
import com.zpd.incident.dto.response.AssignedCaseResponse;
import com.zpd.incident.dto.response.CaseDetailResponse;
import com.zpd.incident.dto.response.CaseSummaryResponse;
import com.zpd.incident.dto.response.ReportedCaseResponse;
import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.service.CaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 사건 목록의 필터·페이징 조회와 단건 상세 조회 요청을 처리하는 REST Controller
@RestController
@RequestMapping("/api/v1/cases")
public class CaseController {

    private final CaseService caseService;
    private final CurrentUserProvider currentUserProvider;

    public CaseController(CaseService caseService, CurrentUserProvider currentUserProvider) {
        this.caseService = caseService;
        this.currentUserProvider = currentUserProvider;
    }

    // 선택 조건과 페이지 정보를 요청 파라미터로 받아 전체 사건 요약 목록 조회
    @GetMapping
    public Map<String, PageResponse<CaseSummaryResponse>> getAllCases(
            @RequestParam(required = false) CaseStatus status,       // 사건 상태 필터
            @RequestParam(required = false) District district,       // 발생 구역 필터
            @RequestParam(required = false) String keyword,          // 사건 제목 검색어
            @RequestParam(required = false) Integer officerId,       // (관리자용) 담당 경찰관 사건 필터
            @RequestParam(defaultValue = "0") int page,              // 조회할 페이지 번호
            @RequestParam(defaultValue = "10") int size,             // 한 페이지에 표시할 사건 수
            @RequestParam(defaultValue = "desc") String sort         // 접수 일시 정렬 방향
    ) {
        // 요청받은 조건을 Service에 전달해 페이징된 사건 요약 결과 조회
        PageResponse<CaseSummaryResponse> response =
                caseService.getAllCases(
                        status,
                        district,
                        keyword,
                        officerId,
                        page,
                        size,
                        sort
                );

        // 공통 성공 응답 형태에 맞춰 조회 결과를 data 항목으로 반환
        return Map.of("data", response);
    }

    // URL로 전달받은 사건 번호에 해당하는 상세 정보를 조회
    @GetMapping("/{caseId}")
    public CaseDetailResponse getCaseById(@PathVariable Integer caseId){
        return caseService.getCaseById(caseId);
    }

    // 시민이 본인 신고 사건 목록을 페이징 조회
    @GetMapping("/reported")
    public Map<String, PageResponse<ReportedCaseResponse>> getReportedCases(
            @RequestParam Integer reporterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort
    ) {
        PageResponse<ReportedCaseResponse> response =
                caseService.getReportedCases(
                        reporterId,
                        page,
                        size,
                        sort
                );

        return Map.of("data", response);
    }

    // 경찰관이 본인 담당 사건 목록을 페이징 조회
    @GetMapping("/assigned")
    public Map<String, PageResponse<AssignedCaseResponse>> getAssignedCases(
            @RequestParam Integer officerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort
    ) {
        PageResponse<AssignedCaseResponse> response =
                caseService.getAssignedCases(
                        officerId,
                        page,
                        size,
                        sort
                );

        return Map.of("data", response);
    }

    @PostMapping
    public ResponseEntity<Map<String, CaseDetailResponse>> createCase(
            @Valid @RequestBody CaseCreateRequest request
            ){
        Integer reporterId = currentUserProvider.getUserId();

        CaseDetailResponse response =
                caseService.createCase(reporterId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("data", response));
    }

    // WAITING 상태의 미배정 사건에 담당 경찰관을 최초 배정한다.
    @PatchMapping("/{caseId}/assignee")
    public ResponseEntity<Map<String, CaseDetailResponse>> assignOfficer(
            @PathVariable Integer caseId,
            @Valid @RequestBody CaseAssignRequest request
    ) {
        // 사건 번호와 요청받은 경찰관 번호를 Service에 전달한다.
        CaseDetailResponse response =
                caseService.assignOfficer(caseId, request);

        // 배정 후 사건 상태와 담당 경찰관 정보를 data 항목으로 반환한다.
        return ResponseEntity
                .ok(Map.of("data", response));
    }

    @PatchMapping("/{caseId}/status")
    public ResponseEntity<Map<String, CaseDetailResponse>> updateCaseStatus(
            @PathVariable Integer caseId,
            @Valid @RequestBody CaseStatusUpdateRequest request
    ){
        CaseDetailResponse response =
                caseService.updateCaseStatus(caseId, request);

        return ResponseEntity
                .ok(Map.of("data", response));
    }
}
