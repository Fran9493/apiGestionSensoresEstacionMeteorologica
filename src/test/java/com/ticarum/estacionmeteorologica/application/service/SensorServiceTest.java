package com.ticarum.estacionmeteorologica.application.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticarum.estacionmeteorologica.application.ports.output.ISensorPersistencePort;
import com.ticarum.estacionmeteorologica.domain.exception.SensorAlreadyExistsException;
import com.ticarum.estacionmeteorologica.domain.exception.SensorNotFoundException;
import com.ticarum.estacionmeteorologica.domain.model.Sensor;

class SensorServiceTest {

	@Mock
    private ISensorPersistencePort sensorPersistencePort;

    @InjectMocks
    private SensorService sensorService;

    private Sensor sensor1;
    private Sensor sensor2;
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);

        //Creamos sensores de prueba
        sensor1 = new Sensor(1, "Temperatura", "ºC", null);
        sensor2 = new Sensor(2, "Humedad", "%", null);
		
	}

	@Test
    void testGuardarSensor() {
		
        when(sensorPersistencePort.comprobarSensorDuplicado("Temperatura")).thenReturn(false);
        when(sensorPersistencePort.nuevoSensor(sensor1)).thenReturn(sensor1);

        Sensor resultado = sensorService.save(sensor1);

        assertNotNull(resultado);
        assertEquals("Temperatura", resultado.getTipo());
        
    }
	
	@Test
    void testGuardarSensor_Duplicado() { //Si ya existe ese mismo tipo de sensor
		
        when(sensorPersistencePort.comprobarSensorDuplicado("Temperatura")).thenReturn(true);

        assertThrows(SensorAlreadyExistsException.class, () -> sensorService.save(sensor1));
        
    }
	
	@Test
    void testBuscarSensorPorId() { //Existe el sensor
		
        when(sensorPersistencePort.buscarSensorPorId(1)).thenReturn(Optional.of(sensor1));

        Sensor resultado = sensorService.findById(1);

        assertNotNull(resultado);
        assertEquals("Temperatura", resultado.getTipo());
        
    }
	
	@Test
    void testBuscarSensorPorId_NoEncontrado() { //No existe el sensor
		
        when(sensorPersistencePort.buscarSensorPorId(999)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> sensorService.findById(999));
        
    }
	
	@Test
    void testObtenerTodosSensores() {
		
        when(sensorPersistencePort.listarSensores()).thenReturn(Arrays.asList(sensor1, sensor2));

        List<Sensor> sensores = sensorService.findAll();

        assertNotNull(sensores);
        assertEquals(2, sensores.size());
        
    }
	
	@Test
    void testObtenerTodosSensores_SinSensores() { //No hay sensores en BBDD
		
        when(sensorPersistencePort.listarSensores()).thenReturn(Arrays.asList());

        assertThrows(SensorNotFoundException.class, () -> sensorService.findAll());
        
    }
	
	@Test
    void testEliminarSensor() { //Si existe
		
        when(sensorPersistencePort.buscarSensorPorId(1)).thenReturn(Optional.of(sensor1));

        assertDoesNotThrow(() -> sensorService.deleteById(1));

        verify(sensorPersistencePort, times(1)).eliminarSensor(1);
        
    }
	
	@Test
    void testEliminarSensor_NoExiste() { //No existe
		
        when(sensorPersistencePort.buscarSensorPorId(999)).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> sensorService.deleteById(999));
        
    }

}
