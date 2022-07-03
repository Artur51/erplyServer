package com.middleware.erply.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${jwt.secret:erply_jwt_key}")
    String secret;

    @Value("${jwt.expirationHours:1}")
    int expirationHours;

    public String generate(
            String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(JwtTokenService.class.getSimpleName())
                    .withKeyId(username)
                    .withExpiresAt(Instant.now().plus(expirationHours, ChronoUnit.HOURS))
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Cannot generate jwt for user " + username, exception);
        }
    }

    public String getVerifyedUsername(
            String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JwtTokenService.class.getSimpleName())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt.getExpiresAtAsInstant().isBefore(Instant.now())) {
                return null;
            }
            return jwt.getKeyId();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
