package com.ticarum.estacionmeteorologica.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ticarum.estacionmeteorologica.security.jwt.JwtAuthenticationFilter;
import com.ticarum.estacionmeteorologica.security.jwt.JwtEntryPoint;
import com.ticarum.estacionmeteorologica.security.jwt.JwtService;
import com.ticarum.estacionmeteorologica.security.user.IUserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtEntryPoint jwtEntryPoint;
    
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//        return new JwtAuthenticationFilter(jwtService, userDetailsService);
//    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
            .csrf(csrf -> csrf.disable()) // 🔹 Deshabilitamos CSRF porque usamos JWT
            .authorizeHttpRequests(auth -> auth
        		.requestMatchers(HttpMethod.GET, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/sensores/**").hasAuthority("ADMIN")
                .requestMatchers(
                		"/auth/**", 
                        "/swagger-ui/**",  // 🔹 Swagger UI
                        "/swagger-ui.html", 
                        "/v3/api-docs/**",  // 🔹 Documentación OpenAPI
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/h2-console/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN") // 🔹 Solo ADMIN accede a /admin/**
                .anyRequest().authenticated() // 🔹 Resto de endpoints requieren autenticación
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
//            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint)) // 🔹 Manejo de errores de autenticación
//            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 🔹 JWT no usa sesiones
//            .authenticationProvider(authenticationProvider) // 🔹 Usamos nuestro AuthenticationProvider
//            //.addFilterBefore(jwtAuthenticationFilter(null, null), UsernamePasswordAuthenticationFilter.class) // 🔹 Agregamos el filtro JWT
//            .headers(headers -> headers.frameOptions().disable());
//
//        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // 🔹 Encriptamos las contraseñas
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager(); // 🔹 Manejador de autenticaciones
//    }
    
}
