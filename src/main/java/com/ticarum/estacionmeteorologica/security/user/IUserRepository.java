package com.ticarum.estacionmeteorologica.security.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByUsername(String username);
	
}
