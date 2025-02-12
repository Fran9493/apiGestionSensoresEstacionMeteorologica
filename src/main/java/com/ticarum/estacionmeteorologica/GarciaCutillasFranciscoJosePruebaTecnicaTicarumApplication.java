package com.ticarum.estacionmeteorologica;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.RegistroEntity;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.SensorEntity;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.IRegistroRepository;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.ISensorRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class GarciaCutillasFranciscoJosePruebaTecnicaTicarumApplication implements CommandLineRunner{
	
	private final ISensorRepository sensorRepository;
	private final IRegistroRepository registroRepository;

	public static void main(String[] args) {
		SpringApplication.run(GarciaCutillasFranciscoJosePruebaTecnicaTicarumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<SensorEntity> sensorEntities = Arrays.asList(
				
				new SensorEntity(null, "Temperatura", "ºC", null),
				new SensorEntity(null, "Humedad", "%", null)
				
				);
		
		sensorRepository.saveAll(sensorEntities);
		
		List<RegistroEntity> registroEntities = Arrays.asList(
				
				new RegistroEntity(null, 22.55, LocalDateTime.of(2025, 01, 22, 22, 31, 12), sensorEntities.get(0)),
				new RegistroEntity(null, 58.82, LocalDateTime.of(2025, 01, 22, 3, 11, 19), sensorEntities.get(1)),
				new RegistroEntity(null, 35.00, LocalDateTime.of(2025, 01, 02, 14, 55, 56), sensorEntities.get(1)),
				new RegistroEntity(null, 7.53, LocalDateTime.of(2025, 02, 02, 9, 40, 21), sensorEntities.get(0)),
				new RegistroEntity(null, 72.18, LocalDateTime.of(2025, 01, 19, 22, 51, 25), sensorEntities.get(1))
				
				);
		
		registroRepository.saveAll(registroEntities);
		
	}

}
