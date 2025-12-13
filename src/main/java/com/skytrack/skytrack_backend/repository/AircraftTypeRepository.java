package com.skytrack.skytrack_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skytrack.skytrack_backend.entity.AircraftType;

public interface AircraftTypeRepository extends JpaRepository<AircraftType, Long> {
    boolean existsByCodeIgnoreCase(String code);
}
