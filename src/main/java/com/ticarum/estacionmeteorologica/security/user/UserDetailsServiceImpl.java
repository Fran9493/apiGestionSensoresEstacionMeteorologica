package com.ticarum.estacionmeteorologica.security.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

public class UserDetailsServiceImpl{

//	private final IUserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
//
//        return User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword()) // 🔹 La contraseña ya debe estar encriptada en la BD
//                .roles(user.getRole().name()) // 🔹 Convertimos el rol en un String
//                .build();
//    }

//    public void registerUser(String username, String password, Role role) {
//        if (userRepository.findByUsername(username).isPresent()) {
//            throw new IllegalArgumentException("El usuario ya existe");
//        }
//        String encryptedPassword = passwordEncoder.encode(password); // 🔹 Encriptamos la contraseña
//        UserEntity newUser = new UserEntity(null, username, encryptedPassword, role);
//        userRepository.save(newUser);
//    }
	
}
