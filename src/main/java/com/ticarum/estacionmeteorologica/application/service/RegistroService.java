package com.ticarum.estacionmeteorologica.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ticarum.estacionmeteorologica.application.ports.input.IRegistroServicePort;
import com.ticarum.estacionmeteorologica.application.ports.output.IRegistroPersistencePort;
import com.ticarum.estacionmeteorologica.domain.exception.RegistroNotFoundException;
import com.ticarum.estacionmeteorologica.domain.model.Registro;
import com.ticarum.estacionmeteorologica.domain.model.Sensor;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.ISensorRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.IRegistroRepository;
import com.ticarum.estacionmeteorologica.utils.GeneradorValorAleatorioRegistro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroService implements IRegistroServicePort{
	
	private final IRegistroPersistencePort registroPersistencePort;
	private final IRegistroRepository registroRepository;
	
	private final SensorService sensorService;
	private final ISensorRestMapper sensorRestMapper;
	
	@Override
	public Registro save(Registro registro) {
		
		return registroPersistencePort.nuevoRegistro(registro);
		
	}
	
	@Override
	public Registro findById(Integer id) {
				
		return registroPersistencePort.buscarRegistroPorId(id)
				.orElseThrow(RegistroNotFoundException::new);
		
	}

	@Override
	public List<Registro> findAll() {
		return registroPersistencePort.listarRegistros();
	}
	
	@Override
	public void deleteById(Integer id) {

		if(registroPersistencePort.buscarRegistroPorId(id).isEmpty()) {			
			
			throw new RegistroNotFoundException();
			
		};
		
		registroPersistencePort.eliminarRegistro(id);

	}

	@Override
	public Registro obtenerRegistroActual(Integer idSensor) {
		
		// Consultamos el sensor
		Sensor sensor = sensorService.findById(idSensor);
		
		Registro registroActual = new Registro(null, GeneradorValorAleatorioRegistro.generaRegistro(sensor.getTipo()), LocalDateTime.now(), sensorRestMapper.toSensorResponse(sensor));
		
		return registroPersistencePort.nuevoRegistro(registroActual);
		
	}

	@Override
	public Double obtenerValorMedioSensorFecha(Integer idSensor, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
				
		// Consultamos el sensor para ver si existe
		Sensor sensor = sensorService.findById(idSensor);
						
		return registroRepository.obtenerValorMedioSensorFecha(idSensor, fechaInicio, fechaFin);
		
	}
	
}
