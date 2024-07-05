package br.com.timeamarelo.gestao_vagas.providers;

import br.com.timeamarelo.gestao_vagas.modules.company.entities.CompanyEntity;
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
public class CompanyJWTProvider {
    @Value("${security.token.secret}")
    private String secretKey;

    public String createToken(CompanyEntity company) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(
                        Instant.now()
                                .plus(Duration.ofHours(2))
                )
                .withClaim("roles", List.of("COMPANY"))
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
