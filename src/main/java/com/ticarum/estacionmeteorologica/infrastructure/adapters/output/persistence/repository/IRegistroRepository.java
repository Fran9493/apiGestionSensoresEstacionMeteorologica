package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.RegistroEntity;

public interface IRegistroRepository extends JpaRepository<RegistroEntity, Integer>{

	@Query("SELECT AVG(r.valor) FROM RegistroEntity r WHERE r.sensor.id = :sensorId AND r.fecha >= :fechaInicio AND r.fecha <= :fechaFin")
    Double obtenerValorMedioSensorFecha(@Param("sensorId") Integer sensorId,
                                        @Param("fechaInicio") LocalDateTime fechaInicio,
                                        @Param("fechaFin") LocalDateTime fechaFin);
	
}
