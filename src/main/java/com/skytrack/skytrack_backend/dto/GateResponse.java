package com.skytrack.skytrack_backend.dto;

public class GateResponse {

    private Long id;
    private String gateNumber;
    private Long airportId;
    private String airportCode;

    public GateResponse() {}

    public GateResponse(Long id, String gateNumber, Long airportId, String airportCode) {
        this.id = id;
        this.gateNumber = gateNumber;
        this.airportId = airportId;
        this.airportCode = airportCode;
    }

    public Long getId() {
        return id;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public Long getAirportId() {
        return airportId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
