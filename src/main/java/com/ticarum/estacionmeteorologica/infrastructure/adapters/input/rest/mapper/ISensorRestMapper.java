package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ticarum.estacionmeteorologica.domain.model.Sensor;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request.SensorCreateRequest;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ISensorRestMapper {

	Sensor toSensor(SensorCreateRequest request);
	
	SensorResponse toSensorResponse(Sensor sensor);
	
	List<SensorResponse> toSensorResponseList(List<Sensor> sensorList);
	
}
