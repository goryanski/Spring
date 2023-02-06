package com.softserve.itacademy.todolist.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenProvider {
    // values from resources/application.properties
    @Value("${jwtExpirationMs}")
    private long expirationMs;

    @Value("${jwtSecret}")
    private String secret;

    public String generateToken(String username) {
        // current time from current timeZone + milliseconds value from application.properties file
        Date expirationDate = Date.from(ZonedDateTime.now().toInstant().plusMillis(expirationMs));
        return JWT.create()
                .withSubject("User details") // what will be stored in the jwt token
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("TodoApp")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("TodoApp")
                .build(); // only token with these data will pass the validation

        DecodedJWT jwt = verifier.verify(token);
        // return username value from token
        return jwt.getClaim("username").asString();
    }
}
