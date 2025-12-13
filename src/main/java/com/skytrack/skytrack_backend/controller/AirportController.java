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

import com.skytrack.skytrack_backend.entity.Airport;
import com.skytrack.skytrack_backend.repository.AirportRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "*")
public class AirportController {

    private final AirportRepository airportRepository;

    public AirportController(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @GetMapping
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getById(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Airport> create(@Valid @RequestBody Airport airport) {
        Airport saved = airportRepository.save(airport);
        return ResponseEntity.created(URI.create("/api/airports/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> update(@PathVariable Long id, @Valid @RequestBody Airport airport) {
        return airportRepository.findById(id)
                .map(existing -> {
                    existing.setCode(airport.getCode());
                    existing.setName(airport.getName());
                    existing.setCity(airport.getCity());
                    existing.setCountry(airport.getCountry());
                    Airport saved = airportRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!airportRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        airportRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
