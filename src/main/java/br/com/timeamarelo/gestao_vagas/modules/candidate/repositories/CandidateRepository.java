package br.com.timeamarelo.gestao_vagas.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
    Optional<CandidateEntity> findByUsername(String username);
}
