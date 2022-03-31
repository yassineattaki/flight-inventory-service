package com.Airxelerate.flightInventoryService.flight.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Airxelerate.flightInventoryService.flight.dto.FlightDTO;
import com.Airxelerate.flightInventoryService.flight.model.Flight;
import com.Airxelerate.flightInventoryService.flight.service.FlightService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class FlightController {
	
	private final FlightService flightService;
	
	@PostMapping("/flights")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.OK)
	public FlightDTO addFlight(@Valid @RequestBody Flight flight) {
		Flight addedFlight = flightService.saveFlight(flight);
		return new FlightDTO(addedFlight);
	}
	
	@GetMapping(value = "/flights/{iataCarrierCode}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.OK)
	public FlightDTO getFlight(@PathVariable String iataCarrierCode) {
		Flight addedFlight = flightService.searchForFlight(iataCarrierCode);
		return new FlightDTO(addedFlight);
	}
	
	@GetMapping(value = "/flights")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.OK)
	public Page<FlightDTO> getFlights(Pageable page) {
		return flightService.getFlights(page).map(FlightDTO::new);
	}
	
	@DeleteMapping(value = "/flights/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.OK)
	public void removeFlight(@PathVariable Long id) {
		flightService.deleteFlight(id);
	}
	
	@GetMapping(value = "/flightsTest")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(code = HttpStatus.OK)
	public Page<FlightDTO> getFlightsTest(Pageable page) {
		return flightService.getFlights(page).map(FlightDTO::new);
	}
	
	

}
