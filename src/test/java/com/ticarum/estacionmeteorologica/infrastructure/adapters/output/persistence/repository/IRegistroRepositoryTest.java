package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.RegistroEntity;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.SensorEntity;
import com.ticarum.estacionmeteorologica.utils.HistoricoRegistro;

@DataJpaTest
@ActiveProfiles("test")
class IRegistroRepositoryTest {

	@Autowired
	IRegistroRepository registroRepository;
	
	@Autowired
	ISensorRepository sensorRepository;
	
	SensorEntity sensor;
	
	@BeforeEach
	void setUp() throws Exception {
		
		//Creamos y guardamos un sensor
		sensor = new SensorEntity();
		sensor.setTipo("Temperatura");
		sensor.setMagnitud("ºC");
		sensor = sensorRepository.save(sensor);
		
		//Creamos y guardamos registros de prueba
		RegistroEntity registro1 = new RegistroEntity(null, 25.5, LocalDateTime.of(2025, 1, 10, 12, 0, 0), sensor);
        RegistroEntity registro2 = new RegistroEntity(null, 22.8, LocalDateTime.of(2025, 1, 11, 14, 0, 0), sensor);
        registroRepository.save(registro1);
        registroRepository.save(registro2);
						
	}
	
	@Test
    void testGuardarRegistro() {
		
		//Creamos y guardamos un registro
        RegistroEntity registro = new RegistroEntity(null, 30.0, LocalDateTime.of(2025, 2, 1, 10, 0, 0), sensor);
        RegistroEntity registroGuardado = registroRepository.save(registro);

        //Comprobamos que se ha guardado y que tiene el valor correcto
        assertNotNull(registroGuardado.getId());
        assertEquals(30.0, registroGuardado.getValor());
        
    }
	
	@Test
	void testBuscarPorId() {
		
		//Consultamos un registro
        Optional<RegistroEntity> buscarRegistro = registroRepository.findById(1);

        //Comprobamos que existe y comparamos su valor        
        assertTrue(buscarRegistro.isPresent());
        assertEquals(22.55, buscarRegistro.get().getValor());
        
    }
	
	@Test
    void testBuscarTodosRegistros() {
		
		//Obtenemos todos los registros
        List<RegistroEntity> registros = registroRepository.findAll();

        //Comprobamos que existen, no son nulos y que están los 2 que hay de prueba
        assertNotNull(registros);
        assertFalse(registros.isEmpty());
        assertEquals(7, registros.size()); //Comprobamos que hay 7, debido a que tenemos que tener en cuenta 
        									//los que se registran al ejecutar la aplicación
        
    }
	
	@Test
    void testBorrarPorId() {
		
		//Borramos el registro con id 1
        registroRepository.deleteById(1);
        
        //Comprobamos que ya no está en la base de datos
        Optional<RegistroEntity> registroBorrado = registroRepository.findById(1);

        assertFalse(registroBorrado.isPresent());
        
    }

	@Test
	void testObtenerValorMedioSensorFecha() {

		LocalDateTime fechaInicio = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2025, 2, 1, 23, 59, 0);
        
        Double valorMedio = registroRepository.obtenerValorMedioSensorFecha(sensor.getId(), fechaInicio, fechaFin);
        
        assertNotNull(valorMedio);
        assertEquals(24.15, valorMedio);
        
	}
	
	@Test
    void testObtenerHistoricoRegistroSensor() {
		
		//Consultamos los registros del sensor de prueba
        List<HistoricoRegistro> historico = registroRepository.obtenerHistoricoRegistroSensor(sensor.getId());

        // Verificamos que hay 2 registros y comprobamos que coincide con los esperados
        assertNotNull(historico);
        assertEquals(2, historico.size());
        assertEquals(25.5, historico.get(0).getValor());
        assertEquals(22.8, historico.get(1).getValor());
        
    }

}
