package com.ticarum.estacionmeteorologica.domain.model;

import java.util.List;

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
public class Sensor {
	
	private Integer id;
	private String tipo;
	private String magnitud;
	private List<Registro> registrosSensor;

}
