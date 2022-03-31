package com.Airxelerate.flightInventoryService.auth.payload.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	@NotBlank
	@ApiModelProperty(position = 0)
	private String username;

	@NotBlank
	@ApiModelProperty(position = 1)
	private String password;

}