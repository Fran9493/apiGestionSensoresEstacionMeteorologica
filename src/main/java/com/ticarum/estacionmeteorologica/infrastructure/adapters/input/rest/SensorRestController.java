package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticarum.estacionmeteorologica.application.service.RegistroService;
import com.ticarum.estacionmeteorologica.application.service.SensorService;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.IRegistroRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.ISensorRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request.SensorCreateRequest;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.RegistroResponse;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;
import com.ticarum.estacionmeteorologica.utils.HistoricoRegistro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Operation(
			summary = "Registrar nuevo sensor",
			description = "Registra un sensor nuevo de un tipo concreto. No pueden existir dos sensores del mismo tipo",
			tags = {"POST"},
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Petición con tipo y magnitud del sensor",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = SensorCreateRequest.class)
							)
					)			
			)
	public ResponseEntity<SensorResponse> registrarSensor(@Valid @RequestBody SensorCreateRequest request) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(sensorRestMapper.toSensorResponse(sensorServicePort.save(sensorRestMapper.toSensor(request))));
		
	}
	
	@DeleteMapping("/{id}")
	@Operation(
			summary = "Eliminar un sensor",
			description = "Elimina un sensor de la base de datos por su ID",
			tags = {"DELETE"}						
			)
	public ResponseEntity<Void> eliminarSensor(@PathVariable Integer id) {
		
		sensorServicePort.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping
	@Operation(
			summary = "Obtiene todos los sensores registrados",
			description = "Obtiene todos los sensores registrados en la base de datos",
			tags = {"GET"}						
			)
	public List<SensorResponse> sensoresRegistrados(){
		
		return sensorRestMapper.toSensorResponseList(sensorServicePort.findAll());
		
	}
	
	@GetMapping("/{idSensor}")
	@Operation(
			summary = "Consultar el valor actual del sensor",
			description = "Devuelve el valor actual del sensor que se le pasa por parámetro el ID",
			tags = {"GET"}
			)
	public ResponseEntity<RegistroResponse> obtenerRegistroActual(@PathVariable Integer idSensor){
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(registroRestMapper.toRegistroResponse(registroServicePort.obtenerRegistroActual(idSensor)));
		
	}
	
	@GetMapping("/{idSensor}/media/{fechaInicio}/{fechaFin}")
	@Operation(
			summary = "Consultar el valor medio de los registros",
			description = "Devuelve el valor medio de todos los registros de un sensor comprendidos en un rango de fechas",
			tags = {"GET"}
			)
	public ResponseEntity<Double> obtenerRegistroMedioSensorFecha(@PathVariable Integer idSensor, 
																  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio, 
																  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin){
		
		Double media = registroServicePort.obtenerValorMedioSensorFecha(idSensor, fechaInicio, fechaFin);
		
		if (media == null) {
			
			return ResponseEntity.noContent().build();
			
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(media);
				
	}
	
	@GetMapping("/{idSensor}/historico")
	@Operation(
			summary = "Consultar el histórico de un sensor",
			description = "Devuelve el histórico de registros de un sensor",
			tags = {"GET"}
			)
	public List<HistoricoRegistro> historicoSensor(@PathVariable Integer idSensor){
		
		return registroServicePort.obtenerHistoricoRegistroSensor(idSensor);
		
	}
		
}
