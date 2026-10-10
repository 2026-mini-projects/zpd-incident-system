package com.zpd.incident.repository;

import com.zpd.incident.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OfficerRepository
        extends JpaRepository<Officer, Integer>,
                JpaSpecificationExecutor<Officer> {

    Optional<Officer> findByUser_Id(Integer userId);
}
