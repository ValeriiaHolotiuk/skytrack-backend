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
import com.skytrack.skytrack_backend.dto.GateRequest;
import com.skytrack.skytrack_backend.dto.GateResponse;
import com.skytrack.skytrack_backend.service.GateService;

@WebMvcTest(GateController.class)
@ActiveProfiles("test")
class GateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GateService gateService;

    @Test
    void getAll_shouldReturnList() throws Exception {
        List<GateResponse> gates = List.of(
                new GateResponse(1L, "A1", 1L, "YYT"),
                new GateResponse(2L, "B2", 2L, "YYZ")
        );

        when(gateService.getAll()).thenReturn(gates);

        mockMvc.perform(get("/api/gates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].gateNumber").value("A1"))
                .andExpect(jsonPath("$[0].airportId").value(1))
                .andExpect(jsonPath("$[0].airportCode").value("YYT"))
                .andExpect(jsonPath("$[1].gateNumber").value("B2"));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        GateRequest req = new GateRequest();
        req.setGateNumber("a1");
        req.setAirportId(1L);

        GateResponse created = new GateResponse(1L, "A1", 1L, "YYT");

        when(gateService.create(any(GateRequest.class))).thenReturn(created);

        mockMvc.perform(post("/api/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gateNumber").value("A1"))
                .andExpect(jsonPath("$.airportId").value(1))
                .andExpect(jsonPath("$.airportCode").value("YYT"));
    }
}
