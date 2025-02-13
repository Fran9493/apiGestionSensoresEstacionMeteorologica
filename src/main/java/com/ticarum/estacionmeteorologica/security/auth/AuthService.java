package com.ticarum.estacionmeteorologica.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticarum.estacionmeteorologica.security.jwt.JwtService;
import com.ticarum.estacionmeteorologica.security.user.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse login(LoginRequest request){
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        
        String token=jwtService.generateToken(user);
        
        return AuthResponse.builder()
            .token(token)
            .build();
        
    }
	
}
