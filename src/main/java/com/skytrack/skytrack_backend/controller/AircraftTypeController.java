package com.skytrack.skytrack_backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skytrack.skytrack_backend.dto.AircraftTypeRequest;
import com.skytrack.skytrack_backend.dto.AircraftTypeResponse;
import com.skytrack.skytrack_backend.service.AircraftTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/aircraft-types")
@CrossOrigin(origins = "*")
public class AircraftTypeController {

    private final AircraftTypeService service;

    public AircraftTypeController(AircraftTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AircraftTypeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftTypeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<AircraftTypeResponse> create(@Valid @RequestBody AircraftTypeRequest request) {
        AircraftTypeResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/aircraft-types/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftTypeResponse> update(@PathVariable Long id,
                                                       @Valid @RequestBody AircraftTypeRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
