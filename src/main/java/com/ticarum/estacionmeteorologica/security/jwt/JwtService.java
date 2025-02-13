package com.ticarum.estacionmeteorologica.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class JwtService {

	private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername()) // Guardamos el username en el token
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día de validez
                .signWith(getKey()) 
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey()) // Verificamos la firma
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject(); // Extraemos el username del payload
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

}
