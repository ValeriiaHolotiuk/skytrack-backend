package com.skytrack.skytrack_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GateRequest {

    @NotBlank(message = "Gate number is required")
    @Size(max = 20, message = "Gate number must be <= 20 characters")
    private String gateNumber;

    @NotNull(message = "Airport id is required")
    private Long airportId;

    public GateRequest() {}

    public GateRequest(String gateNumber, Long airportId) {
        this.gateNumber = gateNumber;
        this.airportId = airportId;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public Long getAirportId() {
        return airportId;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }
}
