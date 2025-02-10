package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response;

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
public class SensorResponse {

	private Integer id;
	private String tipo;
	private String magnitud;
	
}
