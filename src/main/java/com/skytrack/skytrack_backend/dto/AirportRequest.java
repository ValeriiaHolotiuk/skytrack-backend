package com.skytrack.skytrack_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AirportRequest {

    @NotBlank
    @Size(max = 10)
    private String code;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 80)
    private String city;

    @NotBlank
    @Size(max = 80)
    private String country;

    public AirportRequest() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}

