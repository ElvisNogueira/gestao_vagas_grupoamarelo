package br.com.timeamarelo.gestao_vagas.modules.candidate.useCases;

import br.com.timeamarelo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.timeamarelo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.timeamarelo.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.timeamarelo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.timeamarelo.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        var candidate = this.candidateRepository
                .findById(candidateId)
                .orElseThrow(UserNotFoundException::new);

        var job = this.jobRepository
                .findById(jobId)
                .orElseThrow(JobNotFoundException::new);

        var applyJobEntity = ApplyJobEntity.builder()
                .jobId(jobId)
                .candidateId(candidateId)
                .build();

        return this.applyJobRepository.save(applyJobEntity);
    }
}
