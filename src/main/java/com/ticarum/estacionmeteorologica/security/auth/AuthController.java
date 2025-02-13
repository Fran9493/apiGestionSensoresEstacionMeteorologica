package com.ticarum.estacionmeteorologica.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticarum.estacionmeteorologica.security.jwt.JwtService;
import com.ticarum.estacionmeteorologica.security.user.IUserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserRepository userRepository;
    
    @PostMapping("/login")
    @Operation(
			summary = "Identificarse en el sistema",
			description = "Entra como usuario autorizado al sistema",
			tags = {"AUTH"},
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Petición con username y password",
					required = true,
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = LoginRequest.class)
							)
					)			
			)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(new AuthResponse("Error: Campos vacíos"));
        }
    	
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        String token = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponse(token));
        
    }
      		
}
