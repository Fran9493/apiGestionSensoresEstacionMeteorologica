package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ticarum.estacionmeteorologica.domain.model.Registro;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request.RegistroCreateRequest;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.RegistroResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRegistroRestMapper {

	Registro toRegistro(RegistroCreateRequest request);
	
	RegistroResponse toRegistroResponse(Registro registro);
	
	List<RegistroResponse> toRegistroResponseList(List<Registro> registroList);
	
}
