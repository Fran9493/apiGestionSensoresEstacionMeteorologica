package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ticarum.estacionmeteorologica.domain.model.Registro;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.RegistroEntity;

@Mapper(componentModel = "spring")
public interface IRegistroPersistenceMapper {

	RegistroEntity toRegistroEntity(Registro registro);
	
	Registro toRegistro(RegistroEntity registroEntity);
	
	List<Registro> toRegistroList(List<RegistroEntity> registroEntityList);
	
}
