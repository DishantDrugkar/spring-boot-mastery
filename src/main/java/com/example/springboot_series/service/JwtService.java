package com.example.springboot_series.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "VGhpc0lzQVNlY3JldEtleUZvckpXVFRva2VuU2lnbmluZzEyMzQ1Njc4OTA=";

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000L;   // 15 min
   // private static final long ACCESS_TOKEN_EXPIRATION = 30 * 1000L;      // 30 sec

    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;

    // Generate Access Token
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    // Generate Refresh Token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    // Extract Username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate Access Token
    public boolean isTokenValid(String token, String username) {
        Claims claims = extractAllClaims(token);
        String tokenType = claims.get("type", String.class);
        return username.equals(claims.getSubject())
                && "access".equals(tokenType)
                && claims.getExpiration().after(new Date());
    }

    // Validate Refresh Token
    public boolean isRefreshTokenValid(String token, String username) {
        Claims claims = extractAllClaims(token);
        String tokenType = claims.get("type", String.class);
        return username.equals(claims.getSubject())
                && "refresh".equals(tokenType)
                && claims.getExpiration().after(new Date());
    }

    // Extract all claims and verify signature and expiration
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Create Secret Key
    private SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }
}