package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ticarum.estacionmeteorologica.application.ports.output.IRegistroPersistencePort;
import com.ticarum.estacionmeteorologica.domain.model.Registro;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.mapper.IRegistroPersistenceMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.IRegistroRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RegistroPersistenceAdapter implements IRegistroPersistencePort{

	private final IRegistroRepository registroRepository;
	private final IRegistroPersistenceMapper registroPersistenceMapper;
	
	@Override
	public Registro nuevoRegistro(Registro registro) {
		
		return registroPersistenceMapper.toRegistro(registroRepository.save(registroPersistenceMapper.toRegistroEntity(registro)));
		
	}

	@Override
	public Optional<Registro> obtenerRegistroActual(Integer idSensor) {
		
		return registroRepository.findById(idSensor)
				.map(registroPersistenceMapper::toRegistro);
		
	}

	@Override
	public Optional<Registro> obtenerRegistroMedioRango(Integer idSensor, LocalDateTime fechaInicio,
			LocalDateTime fechaFin) {
		
		return Optional.empty();
		
	}

	@Override
	public List<Registro> obtenerHistoricoRegistros(Integer idSensor) {
		// TODO Auto-generated method stub
		return null;
	}

}
