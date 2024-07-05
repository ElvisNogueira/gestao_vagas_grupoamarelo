package br.com.timeamarelo.gestao_vagas.modules.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    public static String objectToJson(Object object) {
        try {
            final var mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID uuid, String secretKey) {
        var algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now()
                .plus(Duration.ofHours(2));

        return JWT.create()
                .withSubject(uuid.toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);
    }
}
