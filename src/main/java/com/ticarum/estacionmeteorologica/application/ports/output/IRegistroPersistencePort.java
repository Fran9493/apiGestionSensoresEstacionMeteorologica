package com.ticarum.estacionmeteorologica.application.ports.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ticarum.estacionmeteorologica.domain.model.Registro;

public interface IRegistroPersistencePort {

	Registro nuevoRegistro (Registro registro);
	
	Optional <Registro> obtenerRegistroActual (Integer idSensor);
	
	Optional <Registro> obtenerRegistroMedioRango (Integer idSensor, LocalDateTime fechaInicio, LocalDateTime fechaFin);
	
	List <Registro> obtenerHistoricoRegistros (Integer idSensor);
	
}
