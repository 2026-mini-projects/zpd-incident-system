package com.zpd.incident.service;

import com.zpd.incident.dto.response.CaseDetailResponse;
import com.zpd.incident.dto.response.CaseOfficerResponse;
import com.zpd.incident.dto.response.CaseReporterResponse;
import com.zpd.incident.dto.response.CaseSummaryResponse;
import com.zpd.incident.entity.IncidentCase;
import com.zpd.incident.exception.CaseNotFoundException;
import com.zpd.incident.repository.IncidentCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 사건 조회와 Entity-Response DTO 변환을 담당하는 Service
@Service
public class CaseService {

    private final IncidentCaseRepository incidentCaseRepository;

    // 생성자에서 Repository를 전달받아 필드에 저장
    public CaseService(IncidentCaseRepository incidentCaseRepository) {
        this.incidentCaseRepository = incidentCaseRepository;
    }

    // 전체 사건을 조회해 목록용 요약 DTO로 변환
    @Transactional(readOnly = true)
    public List<CaseSummaryResponse> getAllCases() {

        return incidentCaseRepository.findAll()
                .stream()
                .map(incidentCase -> new CaseSummaryResponse(
                        incidentCase.getCaseId(),
                        incidentCase.getTitle(),
                        incidentCase.getDistrict(),
                        incidentCase.getCaseStatus(),
                        incidentCase.getCreatedAt()
                )).toList();
    }

    // 사건 번호로 단건 조회하며, 존재하지 않으면 CaseNotFoundException 발생
    @Transactional(readOnly = true)
    public CaseDetailResponse getCaseById(Integer caseId){
        IncidentCase incidentCase = incidentCaseRepository
                .findById(caseId)
                .orElseThrow(() -> new CaseNotFoundException(caseId));

        return toCaseDetailResponse(incidentCase);
    }

    // 사건 Entity와 연관된 신고자·담당 경찰관 정보를 상세 응답 DTO로 변환
    private CaseDetailResponse toCaseDetailResponse(IncidentCase incidentCase){

        // 신고자는 필수 연관관계이므로 항상 응답 DTO로 변환
        CaseReporterResponse caseReporterResponse =
                new CaseReporterResponse(
                incidentCase.getReporter().getId(),
                incidentCase.getReporter().getNickname()
        );

        CaseOfficerResponse caseOfficerResponse = null;

        // 미배정 사건은 담당 경찰관이 없으므로 존재할 때만 응답 DTO로 변환
        if(incidentCase.getOfficer() != null){
            caseOfficerResponse = new CaseOfficerResponse(
                    incidentCase.getOfficer().getId(),
                    incidentCase.getOfficer().getName()
            );
        }

        return new CaseDetailResponse(
                incidentCase.getCaseId(),
                incidentCase.getTitle(),
                incidentCase.getContent(),
                incidentCase.getDistrict(),
                incidentCase.getCaseStatus(),
                incidentCase.getCreatedAt(),
                incidentCase.getClosedAt(),
                caseOfficerResponse,
                caseReporterResponse
        );
    }
}
