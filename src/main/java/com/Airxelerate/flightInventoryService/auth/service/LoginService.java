package com.Airxelerate.flightInventoryService.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Airxelerate.flightInventoryService.auth.model.ERole;
import com.Airxelerate.flightInventoryService.auth.model.RefreshToken;
import com.Airxelerate.flightInventoryService.auth.model.Role;
import com.Airxelerate.flightInventoryService.auth.model.User;
import com.Airxelerate.flightInventoryService.auth.payload.request.LoginRequest;
import com.Airxelerate.flightInventoryService.auth.payload.request.RefreshTokenRequest;
import com.Airxelerate.flightInventoryService.auth.payload.request.SignupRequest;
import com.Airxelerate.flightInventoryService.auth.payload.response.JwtResponse;
import com.Airxelerate.flightInventoryService.auth.payload.response.MessageResponse;
import com.Airxelerate.flightInventoryService.auth.repository.RoleRepository;
import com.Airxelerate.flightInventoryService.auth.repository.UserRepository;
import com.Airxelerate.flightInventoryService.auth.security.jwt.JwtUtils;
import com.Airxelerate.flightInventoryService.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final AuthenticationManager authenticationManager;	
	private final UserRepository userRepository;	
	private final RoleRepository roleRepository;	
	private final PasswordEncoder encoder;	
	private final JwtUtils jwtUtils;
	
	
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = this.jwtUtils.generateJwtToken(userPrincipal);		
		RefreshToken refreshToken = this.jwtUtils.createRefreshToken(userPrincipal);
		
		return new JwtResponse(jwt, refreshToken.getToken());
		
	}
	
	@Transactional
	public MessageResponse registerUser(SignupRequest signUpRequest) {
		if (this.userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new MessageResponse("Error: Username is already taken!");
		}
		User user = new User(signUpRequest.getUsername(),
		this.encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRole();
		List<Role> roles = new ArrayList<>();
		
		if (strRoles == null) {
		Role userRole = this.roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		} else {
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = this.roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);
		
				break;
			default:
				Role userRole = this.roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
		});
		}
		
		user.setRoles(roles);
		this.userRepository.save(user);
		return new MessageResponse("User registered successfully!");
		
	}
	
	public JwtResponse refreshtoken(RefreshTokenRequest refreshTokenRequest) {
		String requestRefreshToken = refreshTokenRequest.getRefreshToken();
		return this.jwtUtils.findByToken(requestRefreshToken)
		        .map(jwtUtils::verifyExpiration)
		        .map(RefreshToken::getUser)
		        .map(user -> {
		          String token = jwtUtils.generateTokenFromUsernameAndRoles(user.getUsername(),user.getRoles());
		          return new JwtResponse(token, requestRefreshToken);
		        }).orElseThrow(() -> new CustomException("Refresh token is not in database!"));
	}
	
	
}
