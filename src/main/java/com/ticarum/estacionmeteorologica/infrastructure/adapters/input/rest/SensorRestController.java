package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticarum.estacionmeteorologica.application.service.RegistroService;
import com.ticarum.estacionmeteorologica.application.service.SensorService;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.IRegistroRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.ISensorRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request.SensorCreateRequest;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.RegistroResponse;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;
import com.ticarum.estacionmeteorologica.utils.HistoricoRegistro;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sensores")
public class SensorRestController {

	private final SensorService sensorServicePort;
	private final RegistroService registroServicePort;
	private final ISensorRestMapper sensorRestMapper;
	private final IRegistroRestMapper registroRestMapper;
	
	@PostMapping
	public ResponseEntity<SensorResponse> registrarSensor(@Valid @RequestBody SensorCreateRequest request) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(sensorRestMapper.toSensorResponse(sensorServicePort.save(sensorRestMapper.toSensor(request))));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarSensor(@PathVariable Integer id) {
		
		sensorServicePort.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping
	public List<SensorResponse> sensoresRegistrados(){
		
		return sensorRestMapper.toSensorResponseList(sensorServicePort.findAll());
		
	}
	
	@GetMapping("/{idSensor}")
	public ResponseEntity<RegistroResponse> obtenerRegistroActual(@PathVariable Integer idSensor){
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(registroRestMapper.toRegistroResponse(registroServicePort.obtenerRegistroActual(idSensor)));
		
	}
	
	@GetMapping("/{idSensor}/media/{fechaInicio}/{fechaFin}")
	public ResponseEntity<Double> obtenerRegistroMedioSensorFecha(@PathVariable Integer idSensor, 
																  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio, 
																  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin){
				
		Double media = registroServicePort.obtenerValorMedioSensorFecha(idSensor, fechaInicio, fechaFin);
				
		if (media == null) {
			
			return ResponseEntity.noContent().build();
			
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(media);
				
	}
	
	@GetMapping("/{idSensor}/historico")
	public List<HistoricoRegistro> historicoSensor(@PathVariable Integer idSensor){
		
		return registroServicePort.obtenerHistoricoRegistroSensor(idSensor);
		
	}
	
	@GetMapping("/registros")
	public List<RegistroResponse> registros() {
		
		return registroRestMapper.toRegistroResponseList(registroServicePort.findAll());
		
	}
	
}
