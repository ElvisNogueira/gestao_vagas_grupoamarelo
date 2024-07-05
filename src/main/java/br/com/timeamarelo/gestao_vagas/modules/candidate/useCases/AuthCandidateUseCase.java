package br.com.timeamarelo.gestao_vagas.modules.candidate.useCases;

import br.com.timeamarelo.gestao_vagas.AuthenticationException;
import br.com.timeamarelo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.timeamarelo.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import br.com.timeamarelo.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import br.com.timeamarelo.gestao_vagas.providers.CandidateJWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateJWTProvider candidateJWTProvider;

    public AuthCandidateResponseDto execute(AuthCandidateRequestDto authCandidateRequestDto) {
        var candidate = candidateRepository.findByUsername(authCandidateRequestDto.getUsername())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username/password incorrect")
                );

        var passwordMatches = this.passwordEncoder
                .matches(authCandidateRequestDto.getPassword(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        var token = candidateJWTProvider.createToken(candidate);
        var expire_in = candidateJWTProvider.validateToken(token)
                .getExpiresAt()
                .toInstant()
                .getEpochSecond();

        return AuthCandidateResponseDto.builder()
                .token(token)
                .expires_in(expire_in)
                .build();
    }

}
