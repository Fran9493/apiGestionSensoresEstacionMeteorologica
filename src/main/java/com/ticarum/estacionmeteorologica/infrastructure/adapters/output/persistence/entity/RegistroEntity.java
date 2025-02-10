package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro")
public class RegistroEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_registro")
	private Integer id;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "fecha")
	private LocalDateTime fecha;
		
	@ManyToOne()
	@JoinColumn(name = "id_sensor", nullable = false)
	private SensorEntity sensor;
	
}
