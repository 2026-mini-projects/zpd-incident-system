package com.zpd.incident.repository;

import com.zpd.incident.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficerRepository extends JpaRepository<Officer, Integer> {

    Optional<Officer> findByUser_Id(Integer userId);
}
