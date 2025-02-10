package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.SensorEntity;

public interface ISensorRepository extends JpaRepository<SensorEntity, Integer>{

	boolean existsByTipoIgnoreCase(String tipoSensor);
	
}
