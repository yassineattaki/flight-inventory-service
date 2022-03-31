package com.Airxelerate.flightInventoryService.auth.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
	
	@NotBlank
	@Size(min = 3, max = 20)
	@ApiModelProperty(position = 0)
	private String username;

	@ApiModelProperty(position = 1)
	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	@ApiModelProperty(position = 2)
	private String password;

}