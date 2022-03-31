package com.Airxelerate.flightInventoryService.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Airxelerate.flightInventoryService.auth.payload.request.LoginRequest;
import com.Airxelerate.flightInventoryService.auth.payload.request.RefreshTokenRequest;
import com.Airxelerate.flightInventoryService.auth.payload.request.SignupRequest;
import com.Airxelerate.flightInventoryService.auth.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1.0/auth")
@Api(tags = "users")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	//@ApiOperation(value = "${UserController.signin}")
//	@ApiResponses(value = {
//	      @ApiResponse(code = 400, message = "Something went wrong"),
//	      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(this.loginService.authenticateUser(loginRequest));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return ResponseEntity.ok(this.loginService.registerUser(signUpRequest));
	}
	
	@PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(this.loginService.refreshtoken(request));
	  }

	
}
