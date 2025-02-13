package com.ticarum.estacionmeteorologica.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;


class GeneradorValorAleatorioRegistroTest {
	
	@RepeatedTest(10)
	void testGenerarRegistro_Temperatura() {
		
		double valor = GeneradorValorAleatorioRegistro.generaRegistro("temperatura");

		assertTrue(valor >= RangoMedicionSensor.SENSOR_TEMPERATURA.getMin()
						&& valor <= RangoMedicionSensor.SENSOR_TEMPERATURA.getMax(),
				"El valor generado para temperatura está fuera del rango esperado");

	}
	
	@RepeatedTest(10)
    void testGenerarRegistro_Humedad() {
		
        double valor = GeneradorValorAleatorioRegistro.generaRegistro("humedad");

        assertTrue(valor >= RangoMedicionSensor.SENSOR_HUMEDAD.getMin() 
                && valor <= RangoMedicionSensor.SENSOR_HUMEDAD.getMax(),
                "El valor generado para humedad está fuera del rango esperado");
        
    }

    @RepeatedTest(10)
    void testGenerarRegistro_Presion() {
    	
        double valor = GeneradorValorAleatorioRegistro.generaRegistro("presion");

        assertTrue(valor >= RangoMedicionSensor.SENSOR_PRESION.getMin() 
                && valor <= RangoMedicionSensor.SENSOR_PRESION.getMax(),
                "El valor generado para presión está fuera del rango esperado");
        
    }

    @RepeatedTest(10)
    void testGenerarRegistro_Viento() {
    	
        double valor = GeneradorValorAleatorioRegistro.generaRegistro("viento");

        assertTrue(valor >= RangoMedicionSensor.SENSOR_VIENTO.getMin() 
                && valor <= RangoMedicionSensor.SENSOR_VIENTO.getMax(),
                "El valor generado para viento está fuera del rango esperado");
        
    }
	
}
