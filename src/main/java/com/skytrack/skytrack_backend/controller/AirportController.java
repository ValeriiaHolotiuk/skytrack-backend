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

import com.skytrack.skytrack_backend.dto.AirportRequest;
import com.skytrack.skytrack_backend.dto.AirportResponse;
import com.skytrack.skytrack_backend.service.AirportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "*")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAll() {
        return ResponseEntity.ok(airportService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AirportResponse> create(@Valid @RequestBody AirportRequest request) {
        AirportResponse created = airportService.create(request);
        return ResponseEntity.created(URI.create("/api/airports/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> update(@PathVariable Long id, @Valid @RequestBody AirportRequest request) {
        return ResponseEntity.ok(airportService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

