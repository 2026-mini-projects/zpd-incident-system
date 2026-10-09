package com.zpd.incident.repository;

import com.zpd.incident.entity.IncidentCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentCaseRepository extends JpaRepository<IncidentCase, Integer> {
}
