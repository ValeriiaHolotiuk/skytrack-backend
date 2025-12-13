package com.skytrack.skytrack_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "gates",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_gate_airport_number", columnNames = {"airport_id", "gate_number"})
        }
)
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gate_number", nullable = false, length = 20)
    private String gateNumber;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_id", nullable = false)
    private Airport airport;

    public Gate() {}

    public Gate(Long id, String gateNumber, Airport airport) {
        this.id = id;
        this.gateNumber = gateNumber;
        this.airport = airport;
    }

    public Long getId() {
        return id;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
