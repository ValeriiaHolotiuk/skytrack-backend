package com.skytrack.skytrack_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.skytrack.skytrack_backend.dto.FlightRequest;
import com.skytrack.skytrack_backend.dto.FlightResponse;
import com.skytrack.skytrack_backend.entity.Airline;
import com.skytrack.skytrack_backend.entity.Airport;
import com.skytrack.skytrack_backend.entity.Flight;
import com.skytrack.skytrack_backend.entity.FlightDirection;
import com.skytrack.skytrack_backend.entity.FlightStatus;
import com.skytrack.skytrack_backend.exception.ResourceNotFoundException;
import com.skytrack.skytrack_backend.repository.AirlineRepository;
import com.skytrack.skytrack_backend.repository.AirportRepository;
import com.skytrack.skytrack_backend.repository.FlightRepository;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    public FlightService(FlightRepository flightRepository,
                         AirlineRepository airlineRepository,
                         AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
    }

    public List<FlightResponse> getAll() {
        return flightRepository.findAll().stream().map(this::toResponse).toList();
    }

    public FlightResponse getById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return toResponse(flight);
    }

    public List<FlightResponse> getByOriginAirport(Long airportId) {
        return flightRepository.findByOriginAirportId(airportId).stream().map(this::toResponse).toList();
    }

    public List<FlightResponse> getByDestinationAirport(Long airportId) {
        return flightRepository.findByDestinationAirportId(airportId).stream().map(this::toResponse).toList();
    }

 
    public List<FlightResponse> getByAirportAndDirection(Long airportId, FlightDirection direction) {
        
        airportRepository.findById(airportId)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + airportId));

        List<Flight> flights = switch (direction) {
            case ARRIVAL -> flightRepository.findByDestinationAirportId(airportId);
            case DEPARTURE -> flightRepository.findByOriginAirportId(airportId);
        };

        return flights.stream().map(this::toResponse).toList();
    }

    public FlightResponse create(FlightRequest req) {
        String flightNumber = req.getFlightNumber().trim().toUpperCase();

        if (flightRepository.existsByFlightNumber(flightNumber)) {
            throw new IllegalArgumentException("Flight number already exists: " + flightNumber);
        }

        Airline airline = airlineRepository.findById(req.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + req.getAirlineId()));

        Airport origin = airportRepository.findById(req.getOriginAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Origin airport not found with id: " + req.getOriginAirportId()));

        Airport destination = airportRepository.findById(req.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found with id: " + req.getDestinationAirportId()));

        if (origin.getId().equals(destination.getId())) {
            throw new IllegalArgumentException("Origin and destination airports cannot be the same.");
        }

        LocalDateTime dep = LocalDateTime.parse(req.getDepartureTime().trim());
        LocalDateTime arr = LocalDateTime.parse(req.getArrivalTime().trim());

        if (arr.isBefore(dep)) {
            throw new IllegalArgumentException("Arrival time cannot be before departure time.");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(flightNumber);
        flight.setDirection(parseDirection(req.getDirection()));
        flight.setStatus(parseStatus(req.getStatus()));
        flight.setDepartureTime(dep);
        flight.setArrivalTime(arr);
        flight.setAirline(airline);
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);

        return toResponse(flightRepository.save(flight));
    }

    public FlightResponse update(Long id, FlightRequest req) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));

        String newNumber = req.getFlightNumber().trim().toUpperCase();
        if (!flight.getFlightNumber().equalsIgnoreCase(newNumber) && flightRepository.existsByFlightNumber(newNumber)) {
            throw new IllegalArgumentException("Flight number already exists: " + newNumber);
        }

        Airline airline = airlineRepository.findById(req.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + req.getAirlineId()));

        Airport origin = airportRepository.findById(req.getOriginAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Origin airport not found with id: " + req.getOriginAirportId()));

        Airport destination = airportRepository.findById(req.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found with id: " + req.getDestinationAirportId()));

        if (origin.getId().equals(destination.getId())) {
            throw new IllegalArgumentException("Origin and destination airports cannot be the same.");
        }

        LocalDateTime dep = LocalDateTime.parse(req.getDepartureTime().trim());
        LocalDateTime arr = LocalDateTime.parse(req.getArrivalTime().trim());
        if (arr.isBefore(dep)) {
            throw new IllegalArgumentException("Arrival time cannot be before departure time.");
        }

        flight.setFlightNumber(newNumber);
        flight.setDirection(parseDirection(req.getDirection()));
        flight.setStatus(parseStatus(req.getStatus()));
        flight.setDepartureTime(dep);
        flight.setArrivalTime(arr);
        flight.setAirline(airline);
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);

        return toResponse(flightRepository.save(flight));
    }

    public void delete(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        flightRepository.delete(flight);
    }

    private FlightStatus parseStatus(String raw) {
        try {
            return FlightStatus.valueOf(raw.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid status. Use: SCHEDULED, BOARDING, DEPARTED, ARRIVED, DELAYED, CANCELLED"
            );
        }
    }

    private FlightDirection parseDirection(String raw) {
        try {
            return FlightDirection.valueOf(raw.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid direction. Use: ARRIVAL or DEPARTURE");
        }
    }

    private FlightResponse toResponse(Flight f) {
   
        return new FlightResponse(
                f.getId(),
                f.getFlightNumber(),
                f.getDirection().name(),
                f.getStatus().name(),
                f.getDepartureTime().toString(),
                f.getArrivalTime().toString(),
                f.getAirline().getId(),
                f.getAirline().getName(),
                f.getOriginAirport().getId(),
                f.getOriginAirport().getCode(),
                f.getDestinationAirport().getId(),
                f.getDestinationAirport().getCode()
        );
    }
}
