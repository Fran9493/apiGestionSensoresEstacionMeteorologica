package com.ticarum.estacionmeteorologica.application.ports.input;

import java.util.List;

import com.ticarum.estacionmeteorologica.domain.model.Registro;

public interface IRegistroServicePort {

	Registro save (Registro registro);
		
	Registro findById (Integer id);
	
	List<Registro> findAll();
	
	void deleteById (Integer id);
	
	Registro obtenerRegistroActual(Integer idSensor);
	
}
