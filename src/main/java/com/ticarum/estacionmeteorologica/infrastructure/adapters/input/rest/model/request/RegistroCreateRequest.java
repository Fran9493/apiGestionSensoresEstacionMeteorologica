package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
public class RegistroCreateRequest {

	@NotNull(message = "El campo valor no puede ser nulo")
	private double valor;
	
	@NotNull(message = "El campo fecha del registro no puede ser nulo")
	private LocalDateTime fecha;
	
	@NotNull(message = "El registro debe ir asociado a un sensor")
	private Integer idSensor;
	
}
