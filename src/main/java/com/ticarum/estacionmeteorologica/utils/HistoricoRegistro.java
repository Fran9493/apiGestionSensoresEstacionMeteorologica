package com.ticarum.estacionmeteorologica.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistoricoRegistro {

	private double valor;
	private LocalDateTime fecha;
	
}
