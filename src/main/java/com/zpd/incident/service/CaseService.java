package com.zpd.incident.service;

import com.zpd.incident.common.PageResponse;
import com.zpd.incident.dto.response.*;
import com.zpd.incident.entity.IncidentCase;
import com.zpd.incident.entity.enums.CaseStatus;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.exception.CaseNotFoundException;
import com.zpd.incident.repository.IncidentCaseRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// 사건 조회, 검색 조건 처리와 Entity-Response DTO 변환을 담당하는 Service
@Service
public class CaseService {

    private final IncidentCaseRepository incidentCaseRepository;

    // 생성자에서 Repository를 전달받아 필드에 저장
    public CaseService(IncidentCaseRepository incidentCaseRepository) {
        this.incidentCaseRepository = incidentCaseRepository;
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

    // 선택 조건과 페이징·접수 일시 정렬을 적용해 사건 요약 목록 조회
    @Transactional(readOnly = true)
    public PageResponse<CaseSummaryResponse> getAllCases(
            CaseStatus status,          // 사건 상태
            District district,          // 발생 구역
            String keyword,             // 사건 제목 검색어
            Integer officerId,
            int page,                   // 조회할 페이지 번호
            int size,                   // 한 페이지에 표시할 사건 수
            String sort                 // 접수 일시 정렬 방향(asc/desc)
    ){

        // 사건 접수 일시를 기준으로 오래된순(ASC) 또는 최신순(DESC) 정렬
        Sort sortCondition = Sort.by(
                "asc".equalsIgnoreCase(sort)
                ? Sort.Direction.ASC
                :Sort.Direction.DESC,
                "createdAt"
        );

        // 페이지 번호, 페이지 크기와 정렬 조건을 하나의 조회 조건으로 구성
        Pageable pageable = PageRequest.of(page, size, sortCondition);

        // 값이 전달된 상태·구역·제목 조건만 동적으로 조합
        Specification<IncidentCase> filter = createFilter(status, district, keyword, officerId);

        // 필터와 페이징 조건을 적용해 사건 Entity 조회
        Page<IncidentCase> incidentCases =
                incidentCaseRepository.findAll(filter, pageable);

        // 페이지 정보를 유지하면서 사건 Entity를 목록용 응답 DTO로 변환
        Page<CaseSummaryResponse> responses =
                incidentCases.map(incidentCase ->
                        new CaseSummaryResponse(
                                incidentCase.getCaseId(),
                                incidentCase.getTitle(),
                                incidentCase.getDistrict(),
                                incidentCase.getCaseStatus(),
                                incidentCase.getCreatedAt()
                        )
                );

        // DTO 목록과 현재 페이지·전체 개수·다음 페이지 여부를 공통 페이징 응답으로 반환
        return new PageResponse<>(
                responses.getContent(),
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.hasNext()
        );
    }

    // 전달된 값이 있는 검색 조건만 추가하고 모든 조건을 AND로 결합
    private Specification<IncidentCase> createFilter(
            CaseStatus status,
            District district,
            String keyword,
            Integer officerId
    ){
        return(root, query, criteriaBuilder) ->{
            List<Predicate> conditions = new ArrayList<>();

            // 사건 상태가 선택된 경우 같은 상태의 사건만 조회
            if(status != null){
                conditions.add(
                        criteriaBuilder.equal(
                                root.get("caseStatus"),
                                status
                        )
                );
            }

            // 발생 구역이 선택된 경우 같은 구역의 사건만 조회
            if(district != null){
                conditions.add(
                        criteriaBuilder.equal(
                                root.get("district"),
                                district
                        )
                );
            }

            // 관리자가 선택한 경찰관이 담당하는 사건만 조회
            if (officerId != null) {
                conditions.add(
                        criteriaBuilder.equal(
                                root.get("officer").get("id"),
                                officerId
                        )
                );
            }

            // 검색어가 있으면 제목에 해당 문자열이 포함된 사건만 조회
            if(keyword != null && !keyword.isBlank()){
                // LIKE 검색의 특수문자(!, %, _)를 일반 문자로 처리
                String escapedKeyword = keyword.trim()
                        .replace("!", "!!")
                        .replace("%", "!%")
                        .replace("_", "!_");

                conditions.add(
                        criteriaBuilder.like(
                                root.get("title"),
                                "%" + escapedKeyword + "%",
                                '!'
                        )
                );
            }

            // 추가된 모든 조건을 AND로 묶어 최종 WHERE 조건 생성
            return criteriaBuilder.and(
                    conditions.toArray(new Predicate[0])
            );
        };
    }

    @Transactional(readOnly = true)
    public PageResponse<ReportedCaseResponse> getReportedCases(
            Integer reporterId,
            int page,
            int size,
            String sort
    ){
        // 접수 일시 기준 정렬 조건 생성
        Sort sortCondition = Sort.by(
                "asc".equalsIgnoreCase(sort)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                "createdAt"
        );

        // 페이지 번호, 크기와 정렬 조건 생성
        Pageable pageable =
                PageRequest.of(page, size, sortCondition);

        // 해당 시민이 신고한 사건 조회
        Page<IncidentCase> reportedCases =
                incidentCaseRepository.findByReporter_Id(
                        reporterId,
                        pageable
                );

        // 사건 Entity를 시민 신고 사건 응답 DTO로 변환
        Page<ReportedCaseResponse> responses =
                reportedCases.map(incidentCase -> {

                    CaseOfficerResponse officerResponse = null;

                    // 아직 담당 경찰관이 없는 사건은 officer를 null로 반환
                    if (incidentCase.getOfficer() != null) {
                        officerResponse = new CaseOfficerResponse(
                                incidentCase.getOfficer().getId(),
                                incidentCase.getOfficer().getName()
                        );
                    }

                    return new ReportedCaseResponse(
                            incidentCase.getCaseId(),
                            incidentCase.getTitle(),
                            incidentCase.getDistrict(),
                            incidentCase.getCaseStatus(),
                            officerResponse,
                            incidentCase.getCreatedAt()
                    );
                });

        // DTO 목록과 페이지 정보를 함께 반환
        return new PageResponse<>(
                responses.getContent(),
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.hasNext()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<AssignedCaseResponse> getAssignedCases(
            Integer officerId,
            int page,
            int size,
            String sort
    ){

        // 사건 접수 일시 기준 정렬
        Sort sortCondition = Sort.by(
                "asc".equalsIgnoreCase(sort)
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                "createdAt"
        );

        // 페이지 번호, 크기와 정렬 조건 생성
        Pageable pageable =
                PageRequest.of(page, size, sortCondition);

        // 해당 경찰관이 담당한 사건 조회
        Page<IncidentCase> assignedCases =
                incidentCaseRepository.findByOfficer_Id(
                        officerId,
                        pageable
                );

        // 사건 Entity를 경찰관 담당 사건 응답 DTO로 변환
        Page<AssignedCaseResponse> responses =
                assignedCases.map(incidentCase -> {

                    CaseReporterResponse reporterResponse =
                            new CaseReporterResponse(
                                    incidentCase.getReporter().getId(),
                                    incidentCase.getReporter().getNickname()
                            );

                    return new AssignedCaseResponse(
                            incidentCase.getCaseId(),
                            incidentCase.getTitle(),
                            incidentCase.getDistrict(),
                            incidentCase.getCaseStatus(),
                            reporterResponse,
                            incidentCase.getCreatedAt()
                    );
                });

        // DTO 목록과 페이지 정보를 반환
        return new PageResponse<>(
                responses.getContent(),
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.hasNext()
        );
    }
}
