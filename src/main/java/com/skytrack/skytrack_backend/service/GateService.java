package com.skytrack.skytrack_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skytrack.skytrack_backend.dto.GateRequest;
import com.skytrack.skytrack_backend.dto.GateResponse;
import com.skytrack.skytrack_backend.entity.Airport;
import com.skytrack.skytrack_backend.entity.Gate;
import com.skytrack.skytrack_backend.exception.ResourceNotFoundException;
import com.skytrack.skytrack_backend.repository.AirportRepository;
import com.skytrack.skytrack_backend.repository.GateRepository;

@Service
public class GateService {

    private final GateRepository gateRepository;
    private final AirportRepository airportRepository;

    public GateService(GateRepository gateRepository, AirportRepository airportRepository) {
        this.gateRepository = gateRepository;
        this.airportRepository = airportRepository;
    }

    public List<GateResponse> getAll() {
        return gateRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public GateResponse getById(Long id) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate not found with id: " + id));
        return toResponse(gate);
    }

    public GateResponse create(GateRequest req) {
        String gateNumber = req.getGateNumber().trim();

        Airport airport = airportRepository.findById(req.getAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + req.getAirportId()));

        if (gateRepository.existsByAirportIdAndGateNumberIgnoreCase(airport.getId(), gateNumber)) {
            throw new IllegalArgumentException("Gate already exists for this airport: " + gateNumber);
        }

        Gate gate = new Gate();
        gate.setGateNumber(gateNumber);
        gate.setAirport(airport);

        return toResponse(gateRepository.save(gate));
    }

    public GateResponse update(Long id, GateRequest req) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate not found with id: " + id));

        String newGateNumber = req.getGateNumber().trim();

        Airport airport = airportRepository.findById(req.getAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + req.getAirportId()));


        boolean changedAirport = !gate.getAirport().getId().equals(airport.getId());
        boolean changedNumber = !gate.getGateNumber().equalsIgnoreCase(newGateNumber);

        if ((changedAirport || changedNumber)
                && gateRepository.existsByAirportIdAndGateNumberIgnoreCase(airport.getId(), newGateNumber)) {
            throw new IllegalArgumentException("Gate already exists for this airport: " + newGateNumber);
        }

        gate.setAirport(airport);
        gate.setGateNumber(newGateNumber);

        return toResponse(gateRepository.save(gate));
    }

    public void delete(Long id) {
        Gate gate = gateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate not found with id: " + id));
        gateRepository.delete(gate);
    }

    private GateResponse toResponse(Gate g) {
        return new GateResponse(
                g.getId(),
                g.getGateNumber(),
                g.getAirport().getId(),
                g.getAirport().getCode()
        );
    }
}
