package com.ticarum.estacionmeteorologica;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.SensorEntity;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.ISensorRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class GarciaCutillasFranciscoJosePruebaTecnicaTicarumApplication implements CommandLineRunner{
	
	private final ISensorRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(GarciaCutillasFranciscoJosePruebaTecnicaTicarumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<SensorEntity> entities = Arrays.asList(
				
				new SensorEntity(null, "Temperatura", "ºC", null),
				new SensorEntity(null, "Humedad", "%", null)
				
				);
		
		repository.saveAll(entities);
		
	}

}
