package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ticarum.estacionmeteorologica.application.ports.output.ISensorPersistencePort;
import com.ticarum.estacionmeteorologica.domain.model.Sensor;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.mapper.ISensorPersistenceMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.ISensorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SensorPersistenceAdapter implements ISensorPersistencePort{
	
	private final ISensorRepository sensorRepository;
	private final ISensorPersistenceMapper sensorPersistenceMapper;

	@Override
	public Sensor registrarSensor(Sensor sensor) {

		return sensorPersistenceMapper.toSensor(sensorRepository.save(sensorPersistenceMapper.toSensorEntity(sensor)));
		
	}
	
	@Override
	public Optional<Sensor> buscarSensorPorId(Integer id) {

		return sensorRepository.findById(id)
				.map(sensorPersistenceMapper::toSensor);
		
	}

	@Override
	public List<Sensor> listarSensores() {
		
		return sensorPersistenceMapper.toSensorList(sensorRepository.findAll());
		
	}

	@Override
	public void eliminarSensor(Integer id) {
		
		sensorRepository.deleteById(id);
		
	}

}
