package com.ticarum.estacionmeteorologica.application.ports.input;

import java.time.LocalDateTime;
import java.util.List;

import com.ticarum.estacionmeteorologica.domain.model.Registro;

public interface IRegistroServicePort {

	Registro save (Registro registro);
		
	Registro findById (Integer idSensor);
	
	Registro obtenerRegistroMedioRango (Integer idSensor, LocalDateTime fechaInicio, LocalDateTime fechaFin);
	
	List <Registro> obtenerHistoricoRegistros (Integer idSensor);
	
}
