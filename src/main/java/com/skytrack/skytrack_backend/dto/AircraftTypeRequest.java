package com.skytrack.skytrack_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AircraftTypeRequest {

    @NotBlank
    @Size(max = 20)
    private String code;

    @NotBlank
    @Size(max = 80)
    private String manufacturer;

    @NotNull
    @Min(1)
    private Integer capacity;

    public String getCode() { return code; }
    public String getManufacturer() { return manufacturer; }
    public Integer getCapacity() { return capacity; }

    public void setCode(String code) { this.code = code; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
