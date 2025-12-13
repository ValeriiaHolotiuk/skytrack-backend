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

import com.skytrack.skytrack_backend.dto.AirlineRequest;
import com.skytrack.skytrack_backend.dto.AirlineResponse;
import com.skytrack.skytrack_backend.service.AirlineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/airlines")
@CrossOrigin(origins = "*")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping
    public ResponseEntity<List<AirlineResponse>> getAll() {
        return ResponseEntity.ok(airlineService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(airlineService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AirlineResponse> create(@Valid @RequestBody AirlineRequest request) {
        AirlineResponse created = airlineService.create(request);
        return ResponseEntity.created(URI.create("/api/airlines/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineResponse> update(@PathVariable Long id, @Valid @RequestBody AirlineRequest request) {
        return ResponseEntity.ok(airlineService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        airlineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
