package com.skytrack.skytrack_backend.dto;

public class AirlineResponse {

    private Long id;
    private String code;
    private String name;
    private String country;

    public AirlineResponse(Long id, String code, String name, String country) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.country = country;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCountry() { return country; }
}
