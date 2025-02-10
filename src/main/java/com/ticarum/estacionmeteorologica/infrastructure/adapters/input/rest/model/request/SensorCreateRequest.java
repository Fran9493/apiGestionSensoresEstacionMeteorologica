package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class SensorCreateRequest {

	@NotBlank(message = "El campo tipo es obligatorio")
	private String tipo;
	
	@NotBlank(message = "El campo magnitud es obligatorio")
	private String magnitud;
	
}
