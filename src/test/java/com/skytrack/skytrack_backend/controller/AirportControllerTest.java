package com.skytrack.skytrack_backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; 
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skytrack.skytrack_backend.dto.AirportRequest;
import com.skytrack.skytrack_backend.dto.AirportResponse;
import com.skytrack.skytrack_backend.service.AirportService;

@WebMvcTest(AirportController.class)
@ActiveProfiles("test")
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AirportService airportService;

    @Test
    void getAll_shouldReturnList() throws Exception {
        List<AirportResponse> airports = List.of(
                new AirportResponse(1L, "YYT", "St. John's International Airport", "St. John's", "Canada"),
                new AirportResponse(2L, "YYZ", "Toronto Pearson International Airport", "Toronto", "Canada")
        );

        when(airportService.getAll()).thenReturn(airports);

        mockMvc.perform(get("/api/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("YYT"))
                .andExpect(jsonPath("$[1].code").value("YYZ"));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        AirportRequest req = new AirportRequest();
        req.setCode("yyt");
        req.setName("St. John's International Airport");
        req.setCity("St. John's");
        req.setCountry("Canada");

        AirportResponse created = new AirportResponse(
                1L, "YYT", "St. John's International Airport", "St. John's", "Canada"
        );

        when(airportService.create(org.mockito.ArgumentMatchers.any(AirportRequest.class)))
                .thenReturn(created);

        mockMvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("YYT"));
    }
}

