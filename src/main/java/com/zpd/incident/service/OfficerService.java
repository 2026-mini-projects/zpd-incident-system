package com.zpd.incident.service;

import com.zpd.incident.repository.OfficerRepository;
import org.springframework.stereotype.Service;
import com.zpd.incident.dto.response.OfficerDetailResponse;
import com.zpd.incident.entity.Officer;
import com.zpd.incident.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.zpd.incident.common.PageResponse;
import com.zpd.incident.dto.response.OfficerSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.zpd.incident.entity.enums.OfficerStatus;
import com.zpd.incident.entity.enums.District;
import com.zpd.incident.entity.enums.OfficerSize;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(readOnly = true)
    public PageResponse<OfficerSummaryResponse> getOfficers(
            int page,
            int size,
            OfficerStatus status,
            District district,
            OfficerSize sizeType,
            String keyword,
            String sort) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "페이지 번호는 0 이상, 크기는 1~100이어야 합니다."
            );
        }

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                createSort(sort)
        );

        Specification<Officer> filter =
                createFilter(status, district, sizeType, keyword);

        Page<Officer> officers =
                officerRepository.findAll(filter, pageRequest);

        Page<OfficerSummaryResponse> responses =
                officers.map(officer -> new OfficerSummaryResponse(
                        officer.getId(),
                        officer.getName(),
                        officer.getUser().getNickname(),
                        officer.getSpecies(),
                        officer.getSize(),
                        officer.getDistrict(),
                        officer.getStatus()
                ));

        return new PageResponse<>(
                responses.getContent(),
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.hasNext()
        );
    }

    private Specification<Officer> createFilter(
            OfficerStatus status,
            District district,
            OfficerSize sizeType,
            String keyword) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();

            if (status != null) {
                conditions.add(
                        criteriaBuilder.equal(root.get("status"), status)
                );
            }

            if (district != null) {
                conditions.add(
                        criteriaBuilder.equal(root.get("district"), district)
                );
            }

            if (sizeType != null) {
                conditions.add(
                        criteriaBuilder.equal(root.get("size"), sizeType)
                );
            }

            if (keyword != null && !keyword.isBlank()) {
                String escapedKeyword = keyword.trim()
                        .replace("!", "!!")
                        .replace("%", "!%")
                        .replace("_", "!_");

                String pattern = "%" + escapedKeyword + "%";

                Predicate nameMatches = criteriaBuilder.like(
                        root.get("name"), pattern, '!'
                );

                Predicate nicknameMatches = criteriaBuilder.like(
                        root.get("user").get("nickname"), pattern, '!'
                );

                conditions.add(
                        criteriaBuilder.or(nameMatches, nicknameMatches)
                );
            }

            return criteriaBuilder.and(
                    conditions.toArray(new Predicate[0])
            );
        };
    }

    private Sort createSort(String sort) {

        if (sort == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "정렬값은 name,asc 형식으로 입력해야 합니다."
            );
        }

        String[] parts = sort.split(",", -1);

        if (parts.length != 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "정렬값은 name,asc 형식으로 입력해야 합니다."
            );
        }

        String property = parts[0].trim();
        String direction = parts[1].trim();

        if (!property.equals("name") && !property.equals("id")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "정렬 기준은 name 또는 id만 가능합니다."
            );
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "정렬 방향은 asc 또는 desc만 가능합니다."
            );
        }

        Sort result = Sort.by(
                Sort.Direction.fromString(direction),
                property
        );

        if (property.equals("name")) {
            result = result.and(Sort.by("id").ascending());
        }

        return result;
    }
}
