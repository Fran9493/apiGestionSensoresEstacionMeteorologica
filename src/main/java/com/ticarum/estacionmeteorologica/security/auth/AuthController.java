package com.ticarum.estacionmeteorologica.security.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticarum.estacionmeteorologica.security.jwt.JwtService;
import com.ticarum.estacionmeteorologica.security.user.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(new AuthResponse("Error: Campos vacíos"));
        }
    	
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        System.out.println("👤 Usuario autenticado: " + userDetails.getUsername());
        System.out.println("🔑 Roles asignados: " + userDetails.getAuthorities());

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
      	
//	private final AuthService authService;
//	
//	@PostMapping(value = "login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
//        
//        return ResponseEntity.ok(authService.login(request));
//        
//    }
	
}
