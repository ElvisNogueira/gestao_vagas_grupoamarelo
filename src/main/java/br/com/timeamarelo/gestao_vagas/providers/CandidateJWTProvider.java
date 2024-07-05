package br.com.timeamarelo.gestao_vagas.providers;

import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class CandidateJWTProvider {
    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public String createToken(CandidateEntity candidate) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now()
                .plus(Duration.ofHours(2));

        return JWT.create().withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withExpiresAt(expiresAt)
                .withClaim("roles", List.of("CANDIDATE"))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        token = token.replace("Bearer ", "");

        return JWT.require(algorithm)
                .build()
                .verify(token);
    }
}
