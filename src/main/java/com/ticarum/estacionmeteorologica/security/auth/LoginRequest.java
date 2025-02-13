package com.ticarum.estacionmeteorologica.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@JsonProperty("username")
	private String username;
	
	@JsonProperty("password")
    private String password;
	
}
