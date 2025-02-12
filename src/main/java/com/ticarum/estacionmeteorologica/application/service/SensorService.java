package com.ticarum.estacionmeteorologica.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticarum.estacionmeteorologica.application.ports.input.ISensorServicePort;
import com.ticarum.estacionmeteorologica.application.ports.output.ISensorPersistencePort;
import com.ticarum.estacionmeteorologica.domain.exception.SensorAlreadyExistsException;
import com.ticarum.estacionmeteorologica.domain.exception.SensorNotFoundException;
import com.ticarum.estacionmeteorologica.domain.model.Sensor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorService implements ISensorServicePort {

	private final ISensorPersistencePort sensorPersistencePort;

	@Override
	public Sensor save(Sensor sensor) {
				
		if(sensorPersistencePort.comprobarSensorDuplicado(sensor.getTipo())) {
			
			throw new SensorAlreadyExistsException();
			
		}

		return sensorPersistencePort.nuevoSensor(sensor);

	}

	@Override
	public Sensor findById(Integer id) {
		
		return sensorPersistencePort.buscarSensorPorId(id)
				.orElseThrow(SensorNotFoundException::new);
	
	}

	@Override
	public List<Sensor> findAll() {

		if(sensorPersistencePort.listarSensores().size() == 0) {
			
			throw new SensorNotFoundException();
			
		}
		
		return sensorPersistencePort.listarSensores();

	}

	@Override
	public void deleteById(Integer id) {

		if(sensorPersistencePort.buscarSensorPorId(id).isEmpty()) {			
			
			throw new SensorNotFoundException();
			
		};
		
		sensorPersistencePort.eliminarSensor(id);

	}

}
