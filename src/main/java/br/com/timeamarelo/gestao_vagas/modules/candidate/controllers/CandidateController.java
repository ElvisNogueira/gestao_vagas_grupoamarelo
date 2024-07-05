package br.com.timeamarelo.gestao_vagas.modules.candidate.controllers;

import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.timeamarelo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.timeamarelo.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.timeamarelo.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.timeamarelo.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.timeamarelo.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.timeamarelo.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;
    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;
    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;
    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Cadastrar candidato na plataforma",
            description = "Essa função faz o cadastro do candidato na plataforma"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = @Content(
                    schema = @Schema(implementation = CandidateEntity.class)
            )
            )
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Retorna o perfil do candidato",
            description = "Essa função exibe o perfil do candidato logado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }
            ),
            @ApiResponse(
                    responseCode = "400"
            )
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest httpServletRequest) {
        try {
            var idCandidate = httpServletRequest.getAttribute("candidate_id");
            var result = this.profileCandidateUseCase.execute(
                    UUID.fromString(idCandidate.toString())
            );

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Listagem de vagas disponíveis para o candidato",
            description = "Essa função lista todas as vagas que tem o filtro na descrição"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<JobEntity>> findJobByFilter(@RequestParam String filter) {
        var jobs = listAllJobsByFilterUseCase.execute(filter);
        return ResponseEntity.ok().body(jobs);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Aplicar-se a uma vaga",
            description = "Essa função aplica o usuário a uma vaga"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ApplyJobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyToJob(
            HttpServletRequest httpServletRequest,
            @RequestBody UUID jobId
    ) {
        try {
            var candidateId = httpServletRequest.getAttribute("candidate_id").toString();
            var result = applyJobCandidateUseCase.execute(UUID.fromString(candidateId), jobId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
