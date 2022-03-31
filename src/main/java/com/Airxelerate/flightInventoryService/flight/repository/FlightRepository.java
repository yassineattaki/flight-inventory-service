package com.Airxelerate.flightInventoryService.flight.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Airxelerate.flightInventoryService.flight.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{
	
	Optional<Flight> findByIataCarrierCode(String iataCarrierCode);
	boolean existsById(Long id);

}
