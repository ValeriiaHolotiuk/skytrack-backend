package com.skytrack.skytrack_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AirlineRequest {

    @NotBlank
    @Size(max = 10)
    private String code;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 80)
    private String country;

    public AirlineRequest() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
