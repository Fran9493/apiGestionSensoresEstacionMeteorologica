package com.ticarum.estacionmeteorologica.infrastructure.adapters.input.rest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ticarum.estacionmeteorologica.domain.exception.SensorAlreadyExistsException;
import com.ticarum.estacionmeteorologica.domain.exception.SensorNotFoundException;
import com.ticarum.estacionmeteorologica.domain.model.ErrorResponse;
import com.ticarum.estacionmeteorologica.utils.ErrorCatalog;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(SensorNotFoundException.class)
	public ErrorResponse handleSensorNotFoundException() {
		
		return ErrorResponse.builder()
				.code(ErrorCatalog.SENSOR_NOT_FOUND.getCode())
				.message(ErrorCatalog.SENSOR_NOT_FOUND.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		BindingResult result = ex.getBindingResult(); //Obtenemos la información de la excepción
		
		return ErrorResponse.builder()
				.code(ErrorCatalog.INVALID_SENSOR.getCode())
				.message(ErrorCatalog.INVALID_SENSOR.getMessage())
				.details(result.getFieldErrors()
						.stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.collect(Collectors.toList())) //De la información de la excepción, obtenemos cada campo individualmente y lo añadimos como lista en details				
				.timestamp(LocalDateTime.now())
				.build();
		
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(SensorAlreadyExistsException.class)
	public ErrorResponse handleSensorAlreadyExistsException() {
		
		return ErrorResponse.builder()
				.code(ErrorCatalog.SENSOR_ALREADY_EXISTS.getCode())
				.message(ErrorCatalog.SENSOR_ALREADY_EXISTS.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleGenericError(Exception ex) {
		
		return ErrorResponse.builder()
				.code(ErrorCatalog.GENERIC_ERROR.getCode())
				.message(ErrorCatalog.GENERIC_ERROR.getMessage())
				.details(Collections.singletonList(ex.getMessage()))
				.timestamp(LocalDateTime.now())
				.build();
		
	}
	
}
