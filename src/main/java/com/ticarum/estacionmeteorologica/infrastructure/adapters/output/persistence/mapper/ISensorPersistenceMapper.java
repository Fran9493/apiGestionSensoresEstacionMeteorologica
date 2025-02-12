package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ticarum.estacionmeteorologica.domain.model.Sensor;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.SensorEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ISensorPersistenceMapper {

	SensorEntity toSensorEntity(Sensor sensor);
	
	Sensor toSensor(SensorEntity sensorEntity);
	
	List<Sensor> toSensorList(List<SensorEntity> sensorEntityList);
	
}
