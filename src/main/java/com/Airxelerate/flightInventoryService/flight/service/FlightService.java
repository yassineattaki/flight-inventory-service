package com.Airxelerate.flightInventoryService.flight.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Airxelerate.flightInventoryService.exception.CustomException;
import com.Airxelerate.flightInventoryService.flight.model.Flight;
import com.Airxelerate.flightInventoryService.flight.repository.FlightRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightService {

	private final FlightRepository flightRepository;
	
	private static Logger LOGGER = LoggerFactory.getLogger(FlightService.class);
	
	public Flight saveFlight(Flight flight) {
		LOGGER.info("Save the flight with the IATA Carrier Code : {}",flight.getIataCarrierCode());
		Flight addedFlight = new Flight(
				flight.getIataCarrierCode().toUpperCase(), 
				flight.getFlightNumber(), 
				flight.getFlightDate(), 
				flight.getOriginFlight().toUpperCase(), 
				flight.getDestinationFlight().toUpperCase());
		return flightRepository.save(addedFlight);
	}
	
	public Flight searchForFlight(String iataCarrierCode) throws CustomException{
		LOGGER.info("Searching for the flight with the IATA Carrier Code : {}",iataCarrierCode);
		return flightRepository.findByIataCarrierCode(iataCarrierCode.toUpperCase())
				.orElseThrow(() -> new CustomException("Flight doesnt exist !"));	
	}
	
	public Page<Flight> getFlights(Pageable pageable){
		LOGGER.info("Retrieve flights ...");
		return flightRepository.findAll(pageable);
	}
	
	public void deleteFlight(Long id) throws CustomException{
		LOGGER.info("Delete the flight with the id : {}", id);
		if (!flightRepository.existsById(id)) {
			LOGGER.error("the flight with the id {} doesnt exitsts", id);
			throw new CustomException("Flight doesnt exist !");
		}
		flightRepository.deleteById(id);
	}
}
