package com.ticarum.estacionmeteorologica.security.jwt;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

	private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS256; // 🔹 Algoritmo de firma

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername()) // 🔹 Guardamos el username en el token
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 🔹 1 día de validez
                .signWith(getKey()) // 🔹 Firma segura con HMAC-SHA256
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey()) // 🔹 Verificamos la firma
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject(); // 🔹 Extraemos el username del payload
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
	
//	private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

//	public String getToken(UserDetails user) {
//		return getToken(new HashMap<>(), user);
//	}
//
//	public String getUsernameFromToken(String token) {
//		return getClaim(token, Claims::getSubject);
//	}
//
//	public boolean isTokenValid(String token, UserDetails userDetails) {
//		final String username = getUsernameFromToken(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//
//	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaims(token);
//		return claimsResolver.apply(claims);
//	}
//
//	private String getToken(Map<String, Object> extraClaims, UserDetails user) {
//		return Jwts.builder().claims(extraClaims).subject(user.getUsername())
//				.issuedAt(new Date(System.currentTimeMillis()))
//				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).signWith(getKey()).compact();
//	}
//
//	private Key getKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
//
//	private Claims getAllClaims(String token) {
//		return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
//	}
//
//	private Date getExpiration(String token) {
//		return getClaim(token, Claims::getExpiration);
//	}
//
//	private boolean isTokenExpired(String token) {
//		return getExpiration(token).before(new Date());
//	}
	
//	public String getToken(UserDetails user) {
//        return getToken(new HashMap<>(), user);
//    }
//    
//    public String getUsernameFromToken(String token) {
//        return getClaim(token, Claims::getSubject);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//    
//    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    
//    private String getToken(Map<String, String> extraClaims, UserDetails user) {
//        return Jwts
//                .builder()
//                .claims().addClaims(extraClaims)
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//    
//    public String generateToken(Map<String, String> extraClaims, String userName, long expireInterval) {
//    	  return Jwts
//    	             .builder()
//    	             .claims().add(extraClaims)
//    	             .and()
//    	             .subject(userName)
//    	             .issuedAt(new Date(System.currentTimeMillis()))
//    	             .expiration(new Date(System.currentTimeMillis() + expireInterval))
//    	             .signWith(getKey())
//    	             .compact();
//    }
//
//    private Key getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    private Claims getAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//    
//    private Date getExpiration(String token) {
//        return getClaim(token, Claims::getExpiration);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return getExpiration(token).before(new Date());
//    }

}
