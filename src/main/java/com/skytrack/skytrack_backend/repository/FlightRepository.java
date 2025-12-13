package com.skytrack.skytrack_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skytrack.skytrack_backend.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);
    List<Flight> findByOriginAirportId(Long originAirportId);
    List<Flight> findByDestinationAirportId(Long destinationAirportId);
}
