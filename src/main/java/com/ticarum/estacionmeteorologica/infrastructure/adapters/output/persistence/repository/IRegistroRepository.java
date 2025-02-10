package com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticarum.estacionmeteorologica.infrastructure.adapters.output.persistence.entity.RegistroEntity;

public interface IRegistroRepository extends JpaRepository<RegistroEntity, Integer>{

}
