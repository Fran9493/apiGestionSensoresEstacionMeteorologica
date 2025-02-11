package com.ticarum.estacionmeteorologica.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.ticarum.estacionmeteorologica.domain.model.Sensor;

public interface ISensorPersistencePort {

	Sensor nuevoSensor(Sensor sensor);
	
	Optional<Sensor> buscarSensorPorId (Integer id);

	List<Sensor> listarSensores();

	void eliminarSensor(Integer id);
	
	boolean comprobarSensorDuplicado(String tipoSensor);

}
