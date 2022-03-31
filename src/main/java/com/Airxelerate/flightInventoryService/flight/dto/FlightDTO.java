package com.Airxelerate.flightInventoryService.flight.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.Airxelerate.flightInventoryService.flight.model.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
	
	@NotBlank
	private String iataCarrierCode;
	@NotBlank
	private String flightNumber;
	@NotBlank
	private LocalDate flightDate;
	@NotBlank
	private String originFlight;
	@NotBlank
	private String destinationFlight;
	
	public FlightDTO(Flight flight) {
		this.iataCarrierCode = flight.getIataCarrierCode();
		this.flightNumber = flight.getFlightNumber();
		this.flightDate = flight.getFlightDate();
		this.originFlight = flight.getOriginFlight();
		this.destinationFlight = flight.getDestinationFlight();
	}
	

}
