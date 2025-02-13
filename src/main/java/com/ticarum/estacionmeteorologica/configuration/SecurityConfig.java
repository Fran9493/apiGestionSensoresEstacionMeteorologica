package com.ticarum.estacionmeteorologica.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ticarum.estacionmeteorologica.security.jwt.JwtAuthenticationFilter;
import com.ticarum.estacionmeteorologica.security.jwt.JwtEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtEntryPoint jwtEntryPoint;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
       return http
            .csrf(csrf -> csrf.disable()) //Deshabilitamos CSRF porque usamos JWT
            .authorizeHttpRequests(auth -> auth
        		.requestMatchers(HttpMethod.GET, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(
                		"/auth/**", 
                        "/swagger-ui/**", 
                        "/swagger-ui.html", 
                        "/v3/api-docs/**",  //
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/h2-console/**"
                ).permitAll()
                .anyRequest().authenticated() //Resto de endpoints requieren autenticación
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint(jwtEntryPoint)
                )
                .sessionManagement(sessionManager
                        -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
       
    }
    
}
