package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticarum.estacionmeteorologica.application.service.SensorService;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.mapper.ISensorRestMapper;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.request.SensorCreateRequest;
import com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest.model.response.SensorResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

	private final SensorService servicePort;
	private final ISensorRestMapper restMapper;
	
	@GetMapping
	public List<SensorResponse> sensoresRegistrados(){
		
		return restMapper.toSensorResponseList(servicePort.findAll());
		
	}
	
	@PostMapping
	public ResponseEntity<SensorResponse> registrarSensor(@Valid @RequestBody SensorCreateRequest request) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(restMapper.toSensorResponse(servicePort.save(restMapper.toSensor(request))));
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminarSensor(@PathVariable Integer id) {
		
		servicePort.deleteById(id);
		
	}
	
}
