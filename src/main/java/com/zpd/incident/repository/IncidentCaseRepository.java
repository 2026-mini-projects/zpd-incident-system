package com.zpd.incident.repository;

import com.zpd.incident.entity.IncidentCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IncidentCaseRepository
        extends JpaRepository<IncidentCase, Integer>,
        JpaSpecificationExecutor<IncidentCase> {

    Page<IncidentCase> findByReporter_Id(
            Integer reporterId,
            Pageable pageable
    );

}
