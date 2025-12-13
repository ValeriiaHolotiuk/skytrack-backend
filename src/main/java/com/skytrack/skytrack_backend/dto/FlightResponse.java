package com.skytrack.skytrack_backend.dto;

public class FlightResponse {

    private Long id;
    private String flightNumber;
    private String status;
    private String departureTime;
    private String arrivalTime;

    private Long airlineId;
    private String airlineName;

    private Long originAirportId;
    private String originCode;

    private Long destinationAirportId;
    private String destinationCode;

    public FlightResponse(Long id, String flightNumber, String status, String departureTime, String arrivalTime,
                          Long airlineId, String airlineName,
                          Long originAirportId, String originCode,
                          Long destinationAirportId, String destinationCode) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.status = status;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.originAirportId = originAirportId;
        this.originCode = originCode;
        this.destinationAirportId = destinationAirportId;
        this.destinationCode = destinationCode;
    }

    public Long getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public String getStatus() { return status; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }

    public Long getAirlineId() { return airlineId; }
    public String getAirlineName() { return airlineName; }

    public Long getOriginAirportId() { return originAirportId; }
    public String getOriginCode() { return originCode; }

    public Long getDestinationAirportId() { return destinationAirportId; }
    public String getDestinationCode() { return destinationCode; }
}
