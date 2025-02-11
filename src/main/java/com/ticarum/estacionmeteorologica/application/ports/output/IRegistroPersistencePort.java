package com.ticarum.estacionmeteorologica.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.ticarum.estacionmeteorologica.domain.model.Registro;

public interface IRegistroPersistencePort {

	Registro nuevoRegistro (Registro registro);
	
	Optional <Registro> buscarRegistroPorId (Integer idRegistro);
	
	List<Registro> listarRegistros();
	
	void eliminarRegistro(Integer id);
	
}
