package com.skytrack.skytrack_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skytrack.skytrack_backend.dto.AirportRequest;
import com.skytrack.skytrack_backend.dto.AirportResponse;
import com.skytrack.skytrack_backend.entity.Airport;
import com.skytrack.skytrack_backend.exception.ResourceNotFoundException;
import com.skytrack.skytrack_backend.repository.AirportRepository;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<AirportResponse> getAll() {
        return airportRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AirportResponse getById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
        return toResponse(airport);
    }

    public AirportResponse create(AirportRequest req) {
        String code = req.getCode().trim().toUpperCase();

        if (airportRepository.existsByCode(code)) {
            throw new IllegalArgumentException("Airport code already exists: " + code);
        }

        Airport airport = new Airport();
        airport.setCode(code);
        airport.setName(req.getName().trim());
        airport.setCity(req.getCity().trim());
        airport.setCountry(req.getCountry().trim());

        Airport saved = airportRepository.save(airport);
        return toResponse(saved);
    }

    public AirportResponse update(Long id, AirportRequest req) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));

        String newCode = req.getCode().trim().toUpperCase();


        if (!airport.getCode().equalsIgnoreCase(newCode) && airportRepository.existsByCode(newCode)) {
            throw new IllegalArgumentException("Airport code already exists: " + newCode);
        }

        airport.setCode(newCode);
        airport.setName(req.getName().trim());
        airport.setCity(req.getCity().trim());
        airport.setCountry(req.getCountry().trim());

        Airport saved = airportRepository.save(airport);
        return toResponse(saved);
    }

    public void delete(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
        airportRepository.delete(airport);
    }

    private AirportResponse toResponse(Airport a) {
        return new AirportResponse(
                a.getId(),
                a.getCode(),
                a.getName(),
                a.getCity(),
                a.getCountry()
        );
    }
}
