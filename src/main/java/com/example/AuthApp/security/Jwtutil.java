package com.example.AuthApp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class Jwtutil {
    private final String SECRET = "mysecretkeymysecretkeymysecretkey12345";
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours expiration period

    private Key getSingingKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    } // converts secret into a key

    public String generateToken(String email) {  // when the user logins successfully
        return Jwts.builder()
                .setSubject(email) // set email inside token
                .setIssuedAt(new Date()) // set creation  date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // set expiry set
                .signWith(getSingingKey()) // signs with secret
                .compact(); // build the token
    }

    public String extractEmail(String token) { // extracts the token and decode email
        return Jwts.parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) { // check whether the token is valid or not
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSingingKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
