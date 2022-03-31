package com.Airxelerate.flightInventoryService.flight.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 3, message = "Minimum code length is 3 characters")
	private String iataCarrierCode;
	
	@NotBlank
	@Size(min = 4, max = 4, message = "Minimum name length is 4 characters")
	private String flightNumber;
	
	private LocalDate flightDate;
	
	@NotBlank
	@Size(min = 3, max = 3, message = "Minimum code length is 3 characters")
	private String originFlight;
	
	@NotBlank
	@Size(min = 3, max = 3, message = "Minimum code length is 3 characters")
	private String destinationFlight;

	public Flight(
			@NotBlank @Size(min = 3, max = 3, message = "Minimum code length is 3 characters") String iataCarrierCode,
			@NotBlank @Size(min = 4, max = 4, message = "Minimum name length is 4 characters") String flightNumber,
			LocalDate flightDate,
			@NotBlank @Size(min = 3, max = 3, message = "Minimum code length is 3 characters") String originFlight,
			@NotBlank @Size(min = 3, max = 3, message = "Minimum code length is 3 characters") String destinationFlight) {
		super();
		this.iataCarrierCode = iataCarrierCode;
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.originFlight = originFlight;
		this.destinationFlight = destinationFlight;
	}

	
}
