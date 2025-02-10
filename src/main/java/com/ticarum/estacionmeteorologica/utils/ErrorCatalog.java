package com.ticarum.estacionmeteorologica.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

	SENSOR_NOT_FOUND("ERR_SENSOR_001", "Sensor not found."),
	INVALID_SENSOR("ERR_SENSOR_002", "Invalid sensor parameters."),
	SENSOR_ALREADY_EXISTS("ERR_SENSOR_003", "Sensor already exists."),
	GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred");
	
	private final String code;
	private final String message;

	ErrorCatalog(String code, String message) {

		this.code = code;
		this.message = message;

	}

	
}
