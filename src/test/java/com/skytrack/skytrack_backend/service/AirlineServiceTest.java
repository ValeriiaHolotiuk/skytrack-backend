package com.skytrack.skytrack_backend.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.skytrack.skytrack_backend.dto.AirlineRequest;
import com.skytrack.skytrack_backend.dto.AirlineResponse;
import com.skytrack.skytrack_backend.entity.Airline;
import com.skytrack.skytrack_backend.exception.ResourceNotFoundException;
import com.skytrack.skytrack_backend.repository.AirlineRepository;

class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineService airlineService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_shouldMapEntitiesToResponses() {
        Airline a1 = new Airline();
        a1.setId(1L);
        a1.setCode("AC");
        a1.setName("Air Canada");
        a1.setCountry("Canada");

        Airline a2 = new Airline();
        a2.setId(2L);
        a2.setCode("WS");
        a2.setName("WestJet");
        a2.setCountry("Canada");

        when(airlineRepository.findAll()).thenReturn(List.of(a1, a2));

        List<AirlineResponse> result = airlineService.getAll();

        assertEquals(2, result.size());
        assertEquals("AC", result.get(0).getCode());
        assertEquals("WestJet", result.get(1).getName());
        verify(airlineRepository).findAll();
    }

    @Test
    void getById_whenExists_shouldReturnResponse() {
        Airline a = new Airline();
        a.setId(5L);
        a.setCode("AC");
        a.setName("Air Canada");
        a.setCountry("Canada");

        when(airlineRepository.findById(5L)).thenReturn(Optional.of(a));

        AirlineResponse result = airlineService.getById(5L);

        assertEquals(5L, result.getId());
        assertEquals("AC", result.getCode());
        verify(airlineRepository).findById(5L);
    }

    @Test
    void getById_whenMissing_shouldThrowResourceNotFound() {
        when(airlineRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> airlineService.getById(99L)
        );

        assertTrue(ex.getMessage().contains("Airline not found with id: 99"));
        verify(airlineRepository).findById(99L);
    }

    @Test
    void create_shouldTrimUppercaseAndSave() {
        AirlineRequest req = new AirlineRequest();
        req.setCode("  ac ");
        req.setName("  Air Canada  ");
        req.setCountry("  Canada ");

        when(airlineRepository.existsByCodeIgnoreCase("AC")).thenReturn(false);

        Airline saved = new Airline();
        saved.setId(1L);
        saved.setCode("AC");
        saved.setName("Air Canada");
        saved.setCountry("Canada");

        when(airlineRepository.save(any(Airline.class))).thenReturn(saved);

        AirlineResponse result = airlineService.create(req);

        assertEquals(1L, result.getId());
        assertEquals("AC", result.getCode());

        ArgumentCaptor<Airline> captor = ArgumentCaptor.forClass(Airline.class);
        verify(airlineRepository).save(captor.capture());
        Airline toSave = captor.getValue();

        assertEquals("AC", toSave.getCode());
        assertEquals("Air Canada", toSave.getName());
        assertEquals("Canada", toSave.getCountry());
    }

    @Test
    void create_whenCodeExists_shouldThrowIllegalArgument() {
        AirlineRequest req = new AirlineRequest();
        req.setCode("ac");
        req.setName("Air Canada");
        req.setCountry("Canada");

        when(airlineRepository.existsByCodeIgnoreCase("AC")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> airlineService.create(req)
        );

        assertTrue(ex.getMessage().contains("Airline code already exists: AC"));
        verify(airlineRepository).existsByCodeIgnoreCase("AC");
        verify(airlineRepository, never()).save(any());
    }

    @Test
    void update_whenNotFound_shouldThrowResourceNotFound() {
        AirlineRequest req = new AirlineRequest();
        req.setCode("WS");
        req.setName("WestJet");
        req.setCountry("Canada");

        when(airlineRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> airlineService.update(50L, req));
        verify(airlineRepository).findById(50L);
        verify(airlineRepository, never()).save(any());
    }

    @Test
    void update_whenChangingToExistingCode_shouldThrowIllegalArgument() {
        Airline existing = new Airline();
        existing.setId(1L);
        existing.setCode("AC");
        existing.setName("Air Canada");
        existing.setCountry("Canada");

        AirlineRequest req = new AirlineRequest();
        req.setCode("WS"); // new code
        req.setName("Air Canada");
        req.setCountry("Canada");

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(airlineRepository.existsByCodeIgnoreCase("WS")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> airlineService.update(1L, req));

        verify(airlineRepository).findById(1L);
        verify(airlineRepository).existsByCodeIgnoreCase("WS");
        verify(airlineRepository, never()).save(any());
    }

    @Test
    void update_whenValid_shouldSaveUpdatedFields() {
        Airline existing = new Airline();
        existing.setId(1L);
        existing.setCode("AC");
        existing.setName("Air Canada");
        existing.setCountry("Canada");

        AirlineRequest req = new AirlineRequest();
        req.setCode("  ws ");
        req.setName("  WestJet  ");
        req.setCountry("  Canada  ");

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(airlineRepository.existsByCodeIgnoreCase("WS")).thenReturn(false);

        Airline saved = new Airline();
        saved.setId(1L);
        saved.setCode("WS");
        saved.setName("WestJet");
        saved.setCountry("Canada");

        when(airlineRepository.save(any(Airline.class))).thenReturn(saved);

        AirlineResponse result = airlineService.update(1L, req);

        assertEquals("WS", result.getCode());
        assertEquals("WestJet", result.getName());

        ArgumentCaptor<Airline> captor = ArgumentCaptor.forClass(Airline.class);
        verify(airlineRepository).save(captor.capture());
        Airline updated = captor.getValue();

        assertEquals("WS", updated.getCode());
        assertEquals("WestJet", updated.getName());
        assertEquals("Canada", updated.getCountry());
    }

    @Test
    void delete_whenExists_shouldDeleteEntity() {
        Airline existing = new Airline();
        existing.setId(3L);

        when(airlineRepository.findById(3L)).thenReturn(Optional.of(existing));

        airlineService.delete(3L);

        verify(airlineRepository).findById(3L);
        verify(airlineRepository).delete(existing);
    }

    @Test
    void delete_whenMissing_shouldThrowResourceNotFound() {
        when(airlineRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> airlineService.delete(404L));
        verify(airlineRepository).findById(404L);
        verify(airlineRepository, never()).delete(any());
    }
}
