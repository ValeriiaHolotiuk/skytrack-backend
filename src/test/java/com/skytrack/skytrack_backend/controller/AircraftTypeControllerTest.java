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
import com.skytrack.skytrack_backend.dto.AircraftTypeRequest;
import com.skytrack.skytrack_backend.dto.AircraftTypeResponse;
import com.skytrack.skytrack_backend.service.AircraftTypeService;

@WebMvcTest(AircraftTypeController.class)
@ActiveProfiles("test")
class AircraftTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AircraftTypeService aircraftTypeService;

    @Test
    void getAll_shouldReturnList() throws Exception {
        List<AircraftTypeResponse> types = List.of(
                new AircraftTypeResponse(1L, "B737", "Boeing", 180),
                new AircraftTypeResponse(2L, "A320", "Airbus", 170)
        );

        when(aircraftTypeService.getAll()).thenReturn(types);

        mockMvc.perform(get("/api/aircraft-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("B737"))
                .andExpect(jsonPath("$[0].manufacturer").value("Boeing"))
                .andExpect(jsonPath("$[0].capacity").value(180))
                .andExpect(jsonPath("$[1].code").value("A320"));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        AircraftTypeRequest req = new AircraftTypeRequest();
        req.setCode("b737");
        req.setManufacturer("Boeing");
        req.setCapacity(180);

        AircraftTypeResponse created =
                new AircraftTypeResponse(1L, "B737", "Boeing", 180);

        when(aircraftTypeService.create(any(AircraftTypeRequest.class)))
                .thenReturn(created);

        mockMvc.perform(post("/api/aircraft-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("B737"))
                .andExpect(jsonPath("$.manufacturer").value("Boeing"))
                .andExpect(jsonPath("$.capacity").value(180));
    }
}
