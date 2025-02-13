package com.ticarum.estacionmeteorologica.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "API Estación Meteorológica UMU",
				description = "API para la gestión de los sensores de la estación meteorológica de la Universidad de Murcia",
				version = "1.0.0",
				contact = @Contact(
						name = "Francisco José García Cutillas",
						email = "fran.garcia93@gmail.com"
						)				
				)
		)
public class SwaggerConfig {

}
