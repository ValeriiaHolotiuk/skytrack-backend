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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skytrack.skytrack_backend.dto.FlightRequest;
import com.skytrack.skytrack_backend.dto.FlightResponse;
import com.skytrack.skytrack_backend.entity.FlightDirection;
import com.skytrack.skytrack_backend.service.FlightService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAll() {
        return ResponseEntity.ok(flightService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }

  
    @GetMapping("/by-airport/{airportId}")
    public ResponseEntity<List<FlightResponse>> getByAirportAndDirection(
            @PathVariable Long airportId,
            @RequestParam FlightDirection direction
    ) {
        return ResponseEntity.ok(flightService.getByAirportAndDirection(airportId, direction));
    }


    @GetMapping("/origin/{airportId}")
    public ResponseEntity<List<FlightResponse>> getByOrigin(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.getByOriginAirport(airportId));
    }

    @GetMapping("/destination/{airportId}")
    public ResponseEntity<List<FlightResponse>> getByDestination(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.getByDestinationAirport(airportId));
    }

    @PostMapping
    public ResponseEntity<FlightResponse> create(@Valid @RequestBody FlightRequest request) {
        FlightResponse created = flightService.create(request);
        return ResponseEntity.created(URI.create("/api/flights/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> update(@PathVariable Long id, @Valid @RequestBody FlightRequest request) {
        return ResponseEntity.ok(flightService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
