package com.zpd.incident.repository;

import com.zpd.incident.entity.IncidentCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IncidentCaseRepository
        extends JpaRepository<IncidentCase, Integer>,
        JpaSpecificationExecutor<IncidentCase> {

}
