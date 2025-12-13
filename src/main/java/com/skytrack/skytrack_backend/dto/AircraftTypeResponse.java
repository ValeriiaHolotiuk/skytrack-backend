package com.skytrack.skytrack_backend.dto;

public class AircraftTypeResponse {

    private Long id;
    private String code;
    private String manufacturer;
    private Integer capacity;

    public AircraftTypeResponse(Long id, String code, String manufacturer, Integer capacity) {
        this.id = id;
        this.code = code;
        this.manufacturer = manufacturer;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getManufacturer() { return manufacturer; }
    public Integer getCapacity() { return capacity; }
}
