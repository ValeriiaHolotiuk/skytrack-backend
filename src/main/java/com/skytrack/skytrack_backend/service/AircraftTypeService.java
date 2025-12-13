package com.skytrack.skytrack_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skytrack.skytrack_backend.dto.AircraftTypeRequest;
import com.skytrack.skytrack_backend.dto.AircraftTypeResponse;
import com.skytrack.skytrack_backend.entity.AircraftType;
import com.skytrack.skytrack_backend.exception.ResourceNotFoundException;
import com.skytrack.skytrack_backend.repository.AircraftTypeRepository;

@Service
public class AircraftTypeService {

    private final AircraftTypeRepository repository;

    public AircraftTypeService(AircraftTypeRepository repository) {
        this.repository = repository;
    }

    public List<AircraftTypeResponse> getAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public AircraftTypeResponse getById(Long id) {
        AircraftType at = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AircraftType not found with id: " + id));
        return toResponse(at);
    }

    public AircraftTypeResponse create(AircraftTypeRequest req) {
        String code = req.getCode().trim().toUpperCase();

        if (repository.existsByCodeIgnoreCase(code)) {
            throw new IllegalArgumentException("Aircraft type code already exists: " + code);
        }

        AircraftType at = new AircraftType();
        at.setCode(code);
        at.setManufacturer(req.getManufacturer().trim());
        at.setCapacity(req.getCapacity());

        return toResponse(repository.save(at));
    }

    public AircraftTypeResponse update(Long id, AircraftTypeRequest req) {
        AircraftType at = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AircraftType not found with id: " + id));

        String newCode = req.getCode().trim().toUpperCase();
        if (!at.getCode().equalsIgnoreCase(newCode) && repository.existsByCodeIgnoreCase(newCode)) {
            throw new IllegalArgumentException("Aircraft type code already exists: " + newCode);
        }

        at.setCode(newCode);
        at.setManufacturer(req.getManufacturer().trim());
        at.setCapacity(req.getCapacity());

        return toResponse(repository.save(at));
    }

    public void delete(Long id) {
        AircraftType at = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AircraftType not found with id: " + id));
        repository.delete(at);
    }

    private AircraftTypeResponse toResponse(AircraftType a) {
        return new AircraftTypeResponse(a.getId(), a.getCode(), a.getManufacturer(), a.getCapacity());
    }
}
