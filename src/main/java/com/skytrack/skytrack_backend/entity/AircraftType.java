package com.skytrack.skytrack_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "aircraft_types",
    uniqueConstraints = @UniqueConstraint(name = "uk_aircraft_code", columnNames = "code")
)
public class AircraftType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String code; 

    @Column(length = 80, nullable = false)
    private String manufacturer; 
    
    @Column(nullable = false)
    private Integer capacity;

    public AircraftType() {}

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getManufacturer() { return manufacturer; }
    public Integer getCapacity() { return capacity; }

    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
