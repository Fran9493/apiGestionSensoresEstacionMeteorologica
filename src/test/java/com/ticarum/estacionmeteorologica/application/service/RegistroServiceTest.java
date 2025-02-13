package com.ticarum.estacionmeteorologica.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticarum.estacionmeteorologica.application.ports.output.IRegistroPersistencePort;
import com.ticarum.estacionmeteorologica.domain.exception.RegistroNotFoundException;
import com.ticarum.estacionmeteorologica.domain.model.Registro;
import com.ticarum.estacionmeteorologica.domain.model.Sensor;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.ISensorRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.mapper.IRegistroPersistenceMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.IRegistroRepository;
import com.ticarum.estacionmeteorologica.utils.HistoricoRegistro;

class RegistroServiceTest {

	@Mock
    private IRegistroRepository registroRepository;
	
	@Mock
	private IRegistroPersistencePort registroPersistencePort;
	
	@Mock
	private IRegistroPersistenceMapper registroPersistenceMapper;
	
	@Mock
    private SensorService sensorService;

    @Mock
    private ISensorRestMapper sensorRestMapper;

    @InjectMocks
    private RegistroService registroService;
        
    private SensorResponse sensor;
    private Sensor toSensor;
    
    //Registros de prueba
    private Registro registro1;
    private Registro registro2;
    
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
		
		//Creamos un sensor de prueba
        sensor = new SensorResponse();
        sensor.setId(1);
        sensor.setTipo("Temperatura");
        sensor.setMagnitud("Celsius");

        //Creamos registros de prueba
        registro1 = new Registro(1, 25.5, LocalDateTime.of(2025, 1, 10, 12, 0), sensor);
        registro2 = new Registro(2, 22.8, LocalDateTime.of(2025, 1, 11, 14, 0), sensor);
        fechaInicio = LocalDateTime.of(2025, 1, 1, 0, 0);
        fechaFin = LocalDateTime.of(2025, 2, 1, 23, 59);
        toSensor = new Sensor();
		toSensor.setTipo(sensor.getTipo());
		toSensor.setMagnitud(sensor.getMagnitud());
		
	}

	@Test
    void testGuardarRegistro() {
		
		when(registroPersistencePort.nuevoRegistro(any(Registro.class))).thenReturn(registro1);

        Registro resultado = registroService.save(registro1);

        assertNotNull(resultado);
        assertEquals(25.5, resultado.getValor());
        
    }
	
	@Test
    void testBuscarRegistroPorId() { //Si existe el registro
		
        when(registroPersistencePort.buscarRegistroPorId(1)).thenReturn(Optional.of(registro1));

        Registro resultado = registroService.findById(1);

        assertNotNull(resultado);
        assertEquals(25.5, resultado.getValor());
        
    }
	
	@Test
    void testBuscarRegistroPorId_NoEncontrado() { //Si no existe el registro
		
        when(registroPersistencePort.buscarRegistroPorId(999)).thenReturn(Optional.empty());

        assertThrows(RegistroNotFoundException.class, () -> registroService.findById(999));
        
    }
	
	@Test
    void testObtenerTodosRegistros() {
		
        when(registroPersistencePort.listarRegistros()).thenReturn(Arrays.asList(registro1, registro2));

        List<Registro> registros = registroService.findAll();

        assertNotNull(registros);
        assertEquals(2, registros.size());
        
    }
	
	@Test
	void testEliminarRegistro() { //Si existe el registro
		
		when(registroPersistencePort.buscarRegistroPorId(1)).thenReturn(Optional.of(registro1));

		assertDoesNotThrow(() -> registroService.deleteById(1));

		verify(registroPersistencePort, times(1)).eliminarRegistro(1);
		
	}
	
	@Test
    void testEliminarRegistro_NoExiste() {//Si no existe el registro
		
        when(registroPersistencePort.buscarRegistroPorId(999)).thenReturn(Optional.empty());

        assertThrows(RegistroNotFoundException.class, () -> registroService.deleteById(999));
        
    }
	
	@Test
    void testObtenerValorMedioSensorFecha() {
				
        when(sensorService.findById(1)).thenReturn(toSensor); 
        when(registroRepository.obtenerValorMedioSensorFecha(1, fechaInicio, fechaFin)).thenReturn(24.15);

        Double resultado = registroService.obtenerValorMedioSensorFecha(1, fechaInicio, fechaFin);

        assertNotNull(resultado);
        assertEquals(24.15, resultado);
        
    }
	
	@Test
    void testObtenerHistoricoRegistroSensor() {
		
        when(sensorService.findById(1)).thenReturn(toSensor);
        List<HistoricoRegistro> historicoEsperado = Arrays.asList(
            new HistoricoRegistro(25.5, LocalDateTime.of(2025, 1, 10, 12, 0)),
            new HistoricoRegistro(22.8, LocalDateTime.of(2025, 1, 11, 14, 0))
        );

        when(registroRepository.obtenerHistoricoRegistroSensor(1)).thenReturn(historicoEsperado);

        List<HistoricoRegistro> resultado = registroService.obtenerHistoricoRegistroSensor(1);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        
    }
	
	@Test
    void testObtenerRegistroActual() {
		
        when(sensorService.findById(1)).thenReturn(toSensor);
        when(sensorRestMapper.toSensorResponse(toSensor)).thenReturn(sensor);
        when(registroPersistencePort.nuevoRegistro(any(Registro.class))).thenReturn(registro1);

        Registro resultado = registroService.obtenerRegistroActual(1);

        assertNotNull(resultado);
        assertEquals(25.5, resultado.getValor());
        
    }
		
}
