package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence;

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
	public Optional<Registro> buscarRegistroPorId(Integer idRegistro) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


	@Override
	public List<Registro> listarRegistros() {
		return registroPersistenceMapper.toRegistroList(registroRepository.findAll());
	}

	@Override
	public void eliminarRegistro(Integer id) {
		
		registroRepository.deleteById(id);
		
	}

}
