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

import com.skytrack.skytrack_backend.dto.GateRequest;
import com.skytrack.skytrack_backend.dto.GateResponse;
import com.skytrack.skytrack_backend.service.GateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gates")
@CrossOrigin(origins = "*")
public class GateController {

    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @GetMapping
    public ResponseEntity<List<GateResponse>> getAll() {
        return ResponseEntity.ok(gateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GateResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GateResponse> create(@Valid @RequestBody GateRequest request) {
        GateResponse created = gateService.create(request);
        return ResponseEntity.created(URI.create("/api/gates/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GateResponse> update(@PathVariable Long id, @Valid @RequestBody GateRequest request) {
        return ResponseEntity.ok(gateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
