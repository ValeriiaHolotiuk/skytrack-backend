package com.skytrack.skytrack_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skytrack.skytrack_backend.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByCode(String code);
}


