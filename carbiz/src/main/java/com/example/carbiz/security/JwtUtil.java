package com.example.carbiz.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key key;
    private final int expHours;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.exp-hours:12}") int expHours
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expHours = expHours;
    }

    public String generateToken(String username, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .addClaims(Map.of("role", role))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expHours, ChronoUnit.HOURS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public String getUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public String getRole(String token) {
        Object r = parseToken(token).getBody().get("role");
        return r == null ? null : r.toString();
    }
}