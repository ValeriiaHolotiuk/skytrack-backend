package com.skytrack.skytrack_backend.controller;

import com.skytrack.skytrack_backend.dto.FlightResponse;
import com.skytrack.skytrack_backend.entity.FlightDirection;
import com.skytrack.skytrack_backend.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    private FlightResponse sampleFlight() {
        return new FlightResponse(
                1L,
                "AC101",
                "DEPARTURE",
                "SCHEDULED",
                "2025-12-14T10:00:00",
                "2025-12-14T12:30:00",
                10L,
                "Air Canada",
                100L,
                "YYZ",
                200L,
                "YUL"
        );
    }

    @Test
    void getAll_shouldReturnFlights() throws Exception {
        when(flightService.getAll()).thenReturn(List.of(sampleFlight()));

        mockMvc.perform(get("/api/flights").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].flightNumber").value("AC101"))
                .andExpect(jsonPath("$[0].direction").value("DEPARTURE"))
                .andExpect(jsonPath("$[0].status").value("SCHEDULED"))
                .andExpect(jsonPath("$[0].airlineName").value("Air Canada"))
                .andExpect(jsonPath("$[0].originCode").value("YYZ"))
                .andExpect(jsonPath("$[0].destinationCode").value("YUL"));
    }

    @Test
    void getById_shouldReturnFlight() throws Exception {
        when(flightService.getById(1L)).thenReturn(sampleFlight());

        mockMvc.perform(get("/api/flights/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.flightNumber").value("AC101"));
    }

    @Test
    void getByAirportAndDirection_shouldReturnFlights() throws Exception {
        when(flightService.getByAirportAndDirection(eq(100L), eq(FlightDirection.DEPARTURE)))
                .thenReturn(List.of(sampleFlight()));

        mockMvc.perform(get("/api/flights/by-airport/100")
                        .param("direction", "DEPARTURE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originAirportId").value(100))
                .andExpect(jsonPath("$[0].direction").value("DEPARTURE"));
    }

    @Test
    void getByOrigin_shouldReturnFlights() throws Exception {
        when(flightService.getByOriginAirport(100L)).thenReturn(List.of(sampleFlight()));

        mockMvc.perform(get("/api/flights/origin/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originAirportId").value(100));
    }

    @Test
    void getByDestination_shouldReturnFlights() throws Exception {
        when(flightService.getByDestinationAirport(200L)).thenReturn(List.of(sampleFlight()));

        mockMvc.perform(get("/api/flights/destination/200").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].destinationAirportId").value(200));
    }
}
