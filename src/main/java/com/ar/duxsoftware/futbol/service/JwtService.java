package com.ar.duxsoftware.futbol.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JwtService {
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    public String generateToken(final String username) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

            return JWT
                    .create()
                    .withSubject(username)
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (final JWTCreationException exception) {
            throw new JWTCreationException("No se pudo generar el token", exception);
        }
    }

    public DecodedJWT validateToken(final String token) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

            return JWT
                    .require(algorithm)
                    .build()
                    .verify(token);
        } catch (final JWTVerificationException exception) {
            throw new JWTVerificationException("No se pudo verificar el token", exception);
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(24).atZone(ZoneId.systemDefault()).toInstant();
    }
}
