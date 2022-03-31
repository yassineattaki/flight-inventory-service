package com.Airxelerate.flightInventoryService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Airxelerate.flightInventoryService.auth.model.ERole;
import com.Airxelerate.flightInventoryService.auth.model.Role;
import com.Airxelerate.flightInventoryService.auth.payload.request.SignupRequest;
import com.Airxelerate.flightInventoryService.auth.repository.RoleRepository;
import com.Airxelerate.flightInventoryService.auth.service.LoginService;
import com.Airxelerate.flightInventoryService.flight.model.Flight;
import com.Airxelerate.flightInventoryService.flight.service.FlightService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class FlightInventoryServiceApplication implements CommandLineRunner{
	
	private final RoleRepository roleRepository;
	private final LoginService loginService;
	private final FlightService flightService;
	
	

	public static void main(String[] args) {
		SpringApplication.run(FlightInventoryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName(ERole.ROLE_ADMIN);
		roleRepository.save(adminRole);
		
		Role userRole = new Role();
		userRole.setName(ERole.ROLE_USER);
		roleRepository.save(userRole);
		
//		User adminUser = new User("admin", "123456");
//		List<Role> adminRoles = new ArrayList<>();
//		adminRoles.add(adminRole);
//		adminUser.setRoles(adminRoles);
//		userRepository.save(adminUser);
		
		SignupRequest adminSignupRequest = new SignupRequest();
		adminSignupRequest.setUsername("admin");
		adminSignupRequest.setPassword("123456");
		Set<String> adminRoles = new HashSet<>();
		adminRoles.add("admin");
		adminSignupRequest.setRole(adminRoles);
		loginService.registerUser(adminSignupRequest);
		
		SignupRequest userSignupRequest = new SignupRequest();
		userSignupRequest.setUsername("user");
		userSignupRequest.setPassword("123456");
		Set<String> userRoles = new HashSet<>();
		userRoles.add("user");
		userSignupRequest.setRole(userRoles);
		loginService.registerUser(userSignupRequest);
		
		
		List<Flight> flights = Arrays.asList(
				new Flight("AAA", "1234",LocalDate.now(), "AAH", "AAL"),
				new Flight("AAB", "1235",LocalDate.now(), "AAR", "JEG"),
				new Flight("AAC", "1236",LocalDate.now(), "AHJ", "ABD"),
				new Flight("AAD", "1237",LocalDate.now(), "ABF", "ABA"),
				new Flight("AAE", "1238",LocalDate.now(), "ABW", "EAB"),
				//new Flight("AAF", "1239",LocalDate.now(), "AEH", "AEA"),
				new Flight("AAG", "1334",LocalDate.now(), "OGO", "ABZ"),
				new Flight("AAH", "1434",LocalDate.now(), "ABR", "APG"),
				new Flight("AAI", "1534",LocalDate.now(), "AHB", "ABJ"),
				new Flight("AAJ", "1264",LocalDate.now(), "ABI", "DYS"),
				new Flight("AAK", "1294",LocalDate.now(), "VJI", "ABG"),
				new Flight("AAL", "1204",LocalDate.now(), "ABO", "AOD"),
				new Flight("AAM", "7234",LocalDate.now(), "AUH", "AUH")
				
				);
		
		for (Flight flight : flights) {
			flightService.saveFlight(flight);
		}
		
		
	}

}
