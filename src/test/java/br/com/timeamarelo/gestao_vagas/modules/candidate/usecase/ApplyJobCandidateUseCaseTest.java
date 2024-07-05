package br.com.timeamarelo.gestao_vagas.modules.candidate.usecase;

import br.com.timeamarelo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.timeamarelo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.timeamarelo.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.timeamarelo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.timeamarelo.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.timeamarelo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.timeamarelo.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();
        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> applyJobCandidateUseCase.execute(candidateId, jobId)
        );

    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();
        var candidate = CandidateEntity.builder()
                .id(candidateId)
                .email("test")
                .curriculum("test")
                .description("description")
                .build();

        Mockito.when(candidateRepository.findById(candidateId))
                .thenReturn(Optional.of(candidate));

        Assertions.assertThrows(
                JobNotFoundException.class,
                () -> applyJobCandidateUseCase.execute(candidateId, jobId)
        );
    }

    @Test
    @DisplayName("Should apply job succesful")
    public void shouldApplyJobSuccessful() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();
        var applyJobId = UUID.randomUUID();
        var candidate = CandidateEntity.builder()
                .id(candidateId)
                .email("test")
                .curriculum("test")
                .description("description")
                .build();
        var job = JobEntity.builder()
                .id(jobId)
                .benefits("Test")
                .description("Test")
                .build();
        var applyJob = ApplyJobEntity.builder()
                .jobId(jobId)
                .candidateId(candidateId)
                .build();

        var applyJobCreated = ApplyJobEntity.builder()
                .id(applyJobId)
                .candidateId(candidateId)
                .jobId(jobId)
                .build();

        Mockito.when(candidateRepository.findById(candidateId))
                .thenReturn(Optional.of(candidate));
        Mockito.when(jobRepository.findById(jobId))
                .thenReturn(Optional.of(job));
        Mockito.when(applyJobRepository.save(applyJob))
                .thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(candidateId, jobId);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(jobId, result.getJobId());
    }
}
