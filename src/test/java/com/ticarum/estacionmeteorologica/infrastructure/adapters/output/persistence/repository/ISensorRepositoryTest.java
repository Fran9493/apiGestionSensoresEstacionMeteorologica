package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.SensorEntity;

@DataJpaTest
@ActiveProfiles("test")
class ISensorRepositoryTest {

	@MockitoBean
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ISensorRepository sensorRepository;
	
	@Test
	void testGuardarSensor() {
		
		//Creamos y guardamos un sensor
		SensorEntity sensor = new SensorEntity();
		sensor.setTipo("Viento");
		sensor.setMagnitud("m/s");
		
		SensorEntity sensorGuardado = sensorRepository.save(sensor);
		
		//Comprobamos que se ha guardado y que tiene el valor correcto
        assertNotNull(sensorGuardado.getId());
        assertEquals("Viento", sensorGuardado.getTipo());
		
	}
	
	@Test
    void testBuscarPorId() {
		
        Optional<SensorEntity> sensorEncontrado = sensorRepository.findById(1);

        assertTrue(sensorEncontrado.isPresent());
        assertEquals("Temperatura", sensorEncontrado.get().getTipo());
        
    }
	
	@Test
    void testBuscarTodosSensores() {
		
        List<SensorEntity> sensores = sensorRepository.findAll();

        assertNotNull(sensores);
        assertFalse(sensores.isEmpty());
        assertEquals(2, sensores.size());
        
    }
	
	@Test
    void testBorrarPorId() {
		
        sensorRepository.deleteById(1);

        Optional<SensorEntity> sensorBorrado = sensorRepository.findById(1);

        assertFalse(sensorBorrado.isPresent());
        
    }

}
