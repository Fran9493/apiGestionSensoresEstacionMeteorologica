package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDateTime;

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
public class RegistroResponse {

	private Integer id;
	private double valor;
	private LocalDateTime fecha;
	private SensorResponse sensor;
	
}
