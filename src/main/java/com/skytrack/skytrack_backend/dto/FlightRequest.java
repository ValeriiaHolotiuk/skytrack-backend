package com.skytrack.skytrack_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FlightRequest {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String status; 

    @NotBlank
    private String departureTime; 

    @NotBlank
    private String arrivalTime; 

    @NotNull
    private Long airlineId;

    @NotNull
    private Long originAirportId;

    @NotNull
    private Long destinationAirportId;

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public Long getAirlineId() { return airlineId; }
    public void setAirlineId(Long airlineId) { this.airlineId = airlineId; }

    public Long getOriginAirportId() { return originAirportId; }
    public void setOriginAirportId(Long originAirportId) { this.originAirportId = originAirportId; }

    public Long getDestinationAirportId() { return destinationAirportId; }
    public void setDestinationAirportId(Long destinationAirportId) { this.destinationAirportId = destinationAirportId; }
}
