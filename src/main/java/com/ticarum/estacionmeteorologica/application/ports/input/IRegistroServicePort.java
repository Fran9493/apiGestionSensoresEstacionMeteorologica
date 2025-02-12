package com.ticarum.estacionmeteorologica.application.ports.input;

import java.time.LocalDateTime;
import java.util.List;

import com.ticarum.estacionmeteorologica.domain.model.Registro;
import com.ticarum.estacionmeteorologica.utils.HistoricoRegistro;

public interface IRegistroServicePort {

	Registro save (Registro registro);
		
	Registro findById (Integer id);
	
	List<Registro> findAll();
	
	void deleteById (Integer id);
	
	Registro obtenerRegistroActual(Integer idSensor);
	
	Double obtenerValorMedioSensorFecha (Integer idSensor, LocalDateTime fechaInicio, LocalDateTime fechaFin);
	
	List<HistoricoRegistro> obtenerHistoricoRegistroSensor(Integer id);
	
}
