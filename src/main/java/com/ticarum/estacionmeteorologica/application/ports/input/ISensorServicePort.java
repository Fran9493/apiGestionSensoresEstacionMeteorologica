package com.ticarum.estacionmeteorologica.application.ports.input;

import java.util.List;

import com.ticarum.estacionmeteorologica.domain.model.Sensor;

public interface ISensorServicePort {

	Sensor save (Sensor sensor);
	
	Sensor findById (Integer id);
	
	List<Sensor> findAll ();
	
	void deleteById (Integer id);
	
}
