package com.ticarum.estacionmeteorologica.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ticarum.estacionmeteorologica.application.ports.input.IRegistroServicePort;
import com.ticarum.estacionmeteorologica.application.ports.output.IRegistroPersistencePort;
import com.ticarum.estacionmeteorologica.domain.model.Registro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroService implements IRegistroServicePort{
	
	private final IRegistroPersistencePort registroPersistencePort;
	
	@Override
	public Registro save(Registro registro) {
		
		return registroPersistencePort.nuevoRegistro(registro);
		
	}

	//Aquí hay que generar el valor aleatorio, mostrarlo y persistirlo en la base de datos
	@Override
	public Registro findById(Integer idSensor) {
		
		return null;
		
	}

	@Override
	public Registro obtenerRegistroMedioRango(Integer idSensor, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registro> obtenerHistoricoRegistros(Integer idSensor) {
		// TODO Auto-generated method stub
		return null;
	}

}
