package com.example.hotel.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration-time.expiration}")
    private  Long expirationTime;
    @Value("${jwt.refresh-token.expiration}")
    private  Long refreshExpirationTime;

    public String generateToken(String email, String role, String fullName) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withClaim("fullName", fullName)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String generateRefreshToken(String email, String role, String fullName) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withClaim("fullName", fullName)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .sign(Algorithm.HMAC256(secretKey));
    }
    public boolean isValidRefreshToken(String refreshToken) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                    .build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            Date expirationDate = decodedJWT.getExpiresAt();
            return expirationDate != null && expirationDate.after(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }


    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getSubject();
    }


    public String getRoleFromToken(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getClaim("role").asString();
    }

    public String getFullNameFromToken(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getClaim("fullName").asString();
    }


    private DecodedJWT decodeJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                .build();
        return verifier.verify(token);
    }
}
