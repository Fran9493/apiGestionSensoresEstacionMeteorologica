package com.ticarum.estacionmeteorologica.security.jwt;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ticarum.estacionmeteorologica.security.user.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
//	//private final ObjectProvider<UserDetailsService> userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//       
//        final String token = getTokenFromRequest(request);
//        final String username;
//
//        if (token==null)
//        {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        username=jwtService.extractUsername(token);
//
//        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
//        {
//            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
//
//            if (jwtService.isTokenValid(token, userDetails))
//            {
//                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities());
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//
//        }
//        
//        filterChain.doFilter(request, response);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
//        {
//            return authHeader.substring(7);
//        }
//        return null;
//    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // 🔹 Obtener el encabezado Authorization
	    System.out.println("🔹 Authorization Header: " + authHeader); // 🔹 Imprimir el valor del encabezado

	    if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
	        System.out.println("🔴 Token no presente o mal formado");
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String token = authHeader.substring(7);
	    System.out.println("🔹 Token extraído: " + token);

	    String username = null;
	    try {
	        username = jwtService.extractUsername(token);
	        System.out.println("🔹 Usuario extraído del token: " + username);
	    } catch (Exception e) {
	        System.out.println("🔴 Error extrayendo el username: " + e.getMessage());
	    }

	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        System.out.println("🔹 Usuario cargado: " + userDetails.getUsername());

	        if (jwtService.isTokenValid(token, userDetails)) {
	            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authToken);
	            System.out.println("✅ Autenticación establecida para: " + userDetails.getUsername());
	        } else {
	            System.out.println("🔴 Token inválido");
	        }
	    }

	    filterChain.doFilter(request, response);
	}

//	private final UserDetailsService userDetailsService;
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		// 🔹 Extraemos el header de autorización
//		String authHeader = request.getHeader("Authorization");
//
//		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return; // 🔹 Si no hay token, dejamos pasar la solicitud sin autenticación
//		}
//
//		// 🔹 Extraemos el token quitando "Bearer "
//		String token = authHeader.substring(7);
//		
//		String username = null;
//	    try {
//	        username = jwtService.extractUsername(token);
//	    } catch (ExpiredJwtException e) {
//	        System.out.println("🔴 JWT expirado: " + e.getMessage());
//	    }
//
//	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//	        // 🔹 Se obtiene dinámicamente o se lanza una excepción si es null
//	        UserDetailsService userDetailsService = userDetailsServiceProvider.getIfAvailable(() -> {
//	            throw new IllegalStateException("🔴 Error: No se pudo obtener UserDetailsService");
//	        });
//
//	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//	        if (jwtService.isTokenValid(token, userDetails)) {
//	            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//	                    userDetails, null, userDetails.getAuthorities());
//	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//	            SecurityContextHolder.getContext().setAuthentication(authToken);
//	        }
//	    }
//
//	    filterChain.doFilter(request, response);
//		String username = jwtService.extractUsername(token); // 🔹 Extraemos el username del token
//
//		// 🔹 Si el usuario no está autenticado en el contexto de seguridad, lo
//		// autenticamos
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//			if (jwtService.isTokenValid(token, userDetails)) {
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
//						null, userDetails.getAuthorities());
//
//				SecurityContextHolder.getContext().setAuthentication(authToken); // 🔹 Establecemos autenticación
//			}
//		}
//
//		filterChain.doFilter(request, response);
//	}

}
