package com.hackacode.themepark.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JWTUtils {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Generar token de acceso
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 10))) //10 horas
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // Validar el token de acceso
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()));
    }

    // Obtener el username del token
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    // Obtener un solo claim
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // Obtener todos los claims del token
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
