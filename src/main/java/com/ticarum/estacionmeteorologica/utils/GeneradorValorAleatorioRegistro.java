package com.ticarum.estacionmeteorologica.utils;

public class GeneradorValorAleatorioRegistro {

	
	public static double generaRegistro(String tipoRegistro) {
				
		double registro;
		
		switch (tipoRegistro.toLowerCase()) {
		case "temperatura": {			
			registro = aleatorio(RangoMedicionSensor.SENSOR_TEMPERATURA.getMin(), RangoMedicionSensor.SENSOR_TEMPERATURA.getMax());
			break;
		}
		case "humedad": {			
			registro = aleatorio(RangoMedicionSensor.SENSOR_HUMEDAD.getMin(), RangoMedicionSensor.SENSOR_HUMEDAD.getMax());
			break;
		}
		case "presion": {			
			registro = aleatorio(RangoMedicionSensor.SENSOR_PRESION.getMin(), RangoMedicionSensor.SENSOR_PRESION.getMax());
			break;
		}
		case "viento": {			
			registro = aleatorio(RangoMedicionSensor.SENSOR_VIENTO.getMin(), RangoMedicionSensor.SENSOR_VIENTO.getMax());
			break;
		}
		default:
			registro = 0.0;
			break;
		}
		
		return registro;
		
	}
	
	private static double aleatorio(double min, double max) {
		
		return min + (Math.random() * (max - min));
		
	}
	
}
