package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticarum.estacionmeteorologica.application.service.RegistroService;
import com.ticarum.estacionmeteorologica.application.service.SensorService;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.IRegistroRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.ISensorRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request.SensorCreateRequest;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.RegistroResponse;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.IRegistroRepository;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository.ISensorRepository;
import com.ticarum.estacionmeteorologica.security.jwt.JwtService;
import com.ticarum.estacionmeteorologica.security.user.IUserRepository;
import com.ticarum.estacionmeteorologica.security.user.UserEntity;
import com.ticarum.estacionmeteorologica.utils.HistoricoRegistro;

@SpringBootTest
@AutoConfigureMockMvc
class SensorRestControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorServicePort;

    @MockitoBean
    private RegistroService registroServicePort;

    @MockitoBean
    private ISensorRestMapper sensorRestMapper;

    @MockitoBean
    private IRegistroRestMapper registroRestMapper;
    
    @MockitoBean
    private ISensorRepository sensorRepository;
    
    @MockitoBean
    private IRegistroRepository registroRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private String token;

    private SensorResponse sensorResponse;
    private SensorCreateRequest sensorRequest;
    private RegistroResponse registroResponse;
    private LocalDateTime fechaInicio, fechaFin;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);

        //Sensor de prueba
        sensorResponse = new SensorResponse(1, "Temperatura", "ºC");
        sensorRequest = new SensorCreateRequest("Temperatura", "ºC");

        //Registro de prueba
        registroResponse = new RegistroResponse(1, 25.5, LocalDateTime.of(2025, 1, 10, 12, 0), sensorResponse);

        //Fechas de prueba
        fechaInicio = LocalDateTime.of(2025, 1, 1, 0, 0);
        fechaFin = LocalDateTime.of(2025, 2, 1, 23, 59);
        
        //Obtenemos el usuario ADMIN
        Optional<UserEntity> adminUser = userRepository.findByUsername("admin");
        
        //Generamos un token JWT para este usuario
        token = jwtService.generateToken(adminUser.get());
		
	}

	@Test
    void testRegistrarSensor() throws Exception {
		
        when(sensorRestMapper.toSensorResponse(any())).thenReturn(sensorResponse);
        when(sensorRestMapper.toSensor(any())).thenReturn(null);
        when(sensorServicePort.save(any())).thenReturn(null);

        mockMvc.perform(post("/sensores")
        		.header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sensorRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tipo").value("Temperatura"))
                .andExpect(jsonPath("$.magnitud").value("ºC"));
        
    }
	
	@Test
    void testEliminarSensor() throws Exception {
		
        doNothing().when(sensorServicePort).deleteById(1);

        mockMvc.perform(delete("/sensores/1")
        		.header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
        
    }
	
	@Test
    void testSensoresRegistrados() throws Exception {
		
        List<SensorResponse> sensores = Arrays.asList(sensorResponse);

        when(sensorServicePort.findAll()).thenReturn(Arrays.asList());
        when(sensorRestMapper.toSensorResponseList(any())).thenReturn(sensores);

        mockMvc.perform(get("/sensores")
        		.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
        
    }
	
	@Test
    void testObtenerRegistroActual() throws Exception {
		
        when(registroServicePort.obtenerRegistroActual(1)).thenReturn(null);
        when(registroRestMapper.toRegistroResponse(any())).thenReturn(registroResponse);

        mockMvc.perform(get("/sensores/1")
        		.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.valor").value(25.5));
        
    }
	
	@Test
    void testObtenerRegistroMedioSensorFecha() throws Exception {
		
        when(registroServicePort.obtenerValorMedioSensorFecha(1, fechaInicio, fechaFin)).thenReturn(24.15);

        mockMvc.perform(get("/sensores/1/media/{fechaInicio}/{fechaFin}", fechaInicio, fechaFin)
        		.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("24.15"));
        
    }
	
	@Test
    void testObtenerRegistroMedioSensorFecha_SinDatos() throws Exception {
		
		when(registroServicePort.obtenerValorMedioSensorFecha(1, fechaInicio, fechaFin)).thenReturn(null);

	    mockMvc.perform(get("/sensores/1/media/{fechaInicio}/{fechaFin}", fechaInicio, fechaFin)
	    		.header("Authorization", "Bearer " + token))
	            .andExpect(status().isNoContent());
        
    }
	
	@Test
    void testHistoricoSensor() throws Exception {
		
        List<HistoricoRegistro> historico = Arrays.asList(
                new HistoricoRegistro(25.5, LocalDateTime.of(2025, 1, 10, 12, 0)),
                new HistoricoRegistro(22.8, LocalDateTime.of(2025, 1, 11, 14, 0))
        );

        when(registroServicePort.obtenerHistoricoRegistroSensor(1)).thenReturn(historico);

        mockMvc.perform(get("/sensores/1/historico")
        		.header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
        
    }
		
}
