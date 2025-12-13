package com.skytrack.skytrack_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skytrack.skytrack_backend.entity.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    boolean existsByCodeIgnoreCase(String code);
}
