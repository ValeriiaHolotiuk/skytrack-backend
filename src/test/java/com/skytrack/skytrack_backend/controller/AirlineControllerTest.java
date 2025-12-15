package com.skytrack.skytrack_backend.controller;

import static org.mockito.ArgumentMatchers.any;
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
import com.skytrack.skytrack_backend.dto.AirlineRequest;
import com.skytrack.skytrack_backend.dto.AirlineResponse;
import com.skytrack.skytrack_backend.service.AirlineService;

@WebMvcTest(AirlineController.class)
@ActiveProfiles("test")
class AirlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AirlineService airlineService;

    @Test
    void getAll_shouldReturnList() throws Exception {
        List<AirlineResponse> airlines = List.of(
                new AirlineResponse(1L, "AC", "Air Canada", "Canada"),
                new AirlineResponse(2L, "WS", "WestJet", "Canada")
        );

        when(airlineService.getAll()).thenReturn(airlines);

        mockMvc.perform(get("/api/airlines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("AC"))
                .andExpect(jsonPath("$[0].name").value("Air Canada"))
                .andExpect(jsonPath("$[0].country").value("Canada"))
                .andExpect(jsonPath("$[1].code").value("WS"));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        AirlineRequest req = new AirlineRequest();
        req.setCode("ac");
        req.setName("Air Canada");
        req.setCountry("Canada");

        AirlineResponse created = new AirlineResponse(1L, "AC", "Air Canada", "Canada");

        when(airlineService.create(any(AirlineRequest.class))).thenReturn(created);

        mockMvc.perform(post("/api/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("AC"))
                .andExpect(jsonPath("$.name").value("Air Canada"))
                .andExpect(jsonPath("$.country").value("Canada"));
    }
}
