package com.ticarum.estacionmeteorologica.utils;

import lombok.Getter;

@Getter
public enum RangoMedicionSensor {

	SENSOR_TEMPERATURA(-50.0, 50.0),
	SENSOR_HUMEDAD(0.0, 100.0),
	SENSOR_PRESION(300.0, 1100.0),
	SENSOR_VIENTO(0.0, 75.0);
	
	
	private final double min;
	private final double max;
	
	private RangoMedicionSensor(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
}
