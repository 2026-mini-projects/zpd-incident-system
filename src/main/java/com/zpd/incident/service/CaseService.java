package com.zpd.incident.service;

import com.zpd.incident.dto.response.CaseSummaryResponse;
import com.zpd.incident.repository.IncidentCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CaseService {

    private final IncidentCaseRepository incidentCaseRepository;

    // 생성자에서 Repository를 전달받아 필드에 저장
    public CaseService(IncidentCaseRepository incidentCaseRepository) {
        this.incidentCaseRepository = incidentCaseRepository;
    }

    // 읽기 전용 트랜잭션 설정
    @Transactional(readOnly = true)

    // CaseSummaryResponse 목록을 반환하는 전체 조회 메서드
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
}