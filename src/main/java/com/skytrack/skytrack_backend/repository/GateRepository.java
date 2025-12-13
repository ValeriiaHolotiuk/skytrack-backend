package com.skytrack.skytrack_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skytrack.skytrack_backend.entity.Gate;

public interface GateRepository extends JpaRepository<Gate, Long> {
    boolean existsByAirportIdAndGateNumberIgnoreCase(Long airportId, String gateNumber);
}
