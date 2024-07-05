package br.com.timeamarelo.gestao_vagas.modules.candidate.useCases;

import br.com.timeamarelo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.timeamarelo.gestao_vagas.modules.candidate.dto.ProfileCandidateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateDto execute(UUID idCandidate) {
        var candidate = candidateRepository.findById(idCandidate)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return ProfileCandidateDto.builder()
                .id(candidate.getId())
                .description(candidate.getDescription())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .username(candidate.getUsername())
                .build();
    }
}
