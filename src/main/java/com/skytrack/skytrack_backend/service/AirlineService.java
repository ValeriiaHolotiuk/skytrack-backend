package com.skytrack.skytrack_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skytrack.skytrack_backend.dto.AirlineRequest;
import com.skytrack.skytrack_backend.dto.AirlineResponse;
import com.skytrack.skytrack_backend.entity.Airline;
import com.skytrack.skytrack_backend.exception.ResourceNotFoundException;
import com.skytrack.skytrack_backend.repository.AirlineRepository;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public List<AirlineResponse> getAll() {
        return airlineRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AirlineResponse getById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + id));
        return toResponse(airline);
    }

    public AirlineResponse create(AirlineRequest req) {
        String code = req.getCode().trim().toUpperCase();

        if (airlineRepository.existsByCodeIgnoreCase(code)) {
            throw new IllegalArgumentException("Airline code already exists: " + code);
        }

        Airline airline = new Airline();
        airline.setCode(code);
        airline.setName(req.getName().trim());
        airline.setCountry(req.getCountry().trim());

        Airline saved = airlineRepository.save(airline);
        return toResponse(saved);
    }

    public AirlineResponse update(Long id, AirlineRequest req) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + id));

        String newCode = req.getCode().trim().toUpperCase();

        if (!airline.getCode().equalsIgnoreCase(newCode) && airlineRepository.existsByCodeIgnoreCase(newCode)) {
            throw new IllegalArgumentException("Airline code already exists: " + newCode);
        }

        airline.setCode(newCode);
        airline.setName(req.getName().trim());
        airline.setCountry(req.getCountry().trim());

        Airline saved = airlineRepository.save(airline);
        return toResponse(saved);
    }

    public void delete(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + id));
        airlineRepository.delete(airline);
    }

    private AirlineResponse toResponse(Airline a) {
        return new AirlineResponse(a.getId(), a.getCode(), a.getName(), a.getCountry());
    }
}
