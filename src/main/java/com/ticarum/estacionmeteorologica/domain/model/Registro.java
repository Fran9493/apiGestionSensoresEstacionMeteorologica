package com.ticarum.estacionmeteorologica.domain.model;

import java.time.LocalDateTime;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registro {
	
	private Integer id;
	private double valor;
	private LocalDateTime fecha;
	private SensorResponse sensor;

}
